import dto.PlayerDTO;
import org.hsqldb.jdbc.JDBCPool;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Database {

    private static final String INSERT_UPDATE_BALANCE_SQL =
            "MERGE INTO PLAYER AS t USING (VALUES(?,?,?)) " +
                    "AS vals(username,transaction_id, balance_change) " +
            "        ON t.USERNAME=vals.username " +
            "    WHEN MATCHED THEN UPDATE " +
                    "SET t.TRANSACTION_ID= vals.transaction_id, " +
                        "t.BALANCE_VERSION = t.BALANCE_VERSION + 1, " +
                        "t.OLD_BALANCE = t.NEW_BALANCE " +
                //FIXME: new to fix java.sql.SQLException: cardinality violation
                //", t.NEW_BALANCE = t.NEW_BALANCE + vals.balance_change " +
            "    WHEN NOT MATCHED THEN INSERT VALUES (vals.username,vals.transaction_id, 1, 0, vals.balance_change)  ";

    private static final String TABLE_CREATION_SQL =
            "CREATE TABLE PLAYER (" +
                    "USERNAME VARCHAR(50) NOT NULL," +
                    "TRANSACTION_ID VARCHAR(50)," +
                    "BALANCE_VERSION INT DEFAULT 0," +
                    "OLD_BALANCE NUMERIC(15,2)," +
                    "NEW_BALANCE NUMERIC(15,2)," +
                    "PRIMARY KEY (USERNAME))";

    private static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS PLAYER";

    private static final String READ_PLAYER_DATA_SQL = "SELECT balance_version, old_balance, new_balance FROM PLAYER WHERE username = ?";

    private static final String UPDATE_NEW_BALANCE_SQL = "UPDATE PLAYER SET NEW_BALANCE = %s WHERE username = '%s'";

    private final JDBCPool jdbcPool;

    public Database() {
        jdbcPool = new JDBCPool(Config.dbMaxPoolSize);
        jdbcPool.setURL(Config.dbURL);
        jdbcPool.setUser(Config.dbUer);
        jdbcPool.setPassword(Config.dbPassword);
        createTable();
    }

    public boolean insetOrUpdateBalance(PlayerDTO dto) {
        boolean results = false;
        try (var connection = jdbcPool.getConnection()) {

            var preparedStatement = connection.prepareStatement(
                    INSERT_UPDATE_BALANCE_SQL
            );
            preparedStatement.setString(1, dto.getUsername());
            preparedStatement.setString(2, dto.getTransactionId());
            preparedStatement.setBigDecimal(3, dto.getBalanceChange());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(READ_PLAYER_DATA_SQL);
            preparedStatement.setString(1, dto.getUsername());
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dto.setBalanceVersion(rs.getInt("balance_version"));
                dto.setOldBalance(rs.getBigDecimal("old_balance"));
            }
            preparedStatement.close();
            //FIXME: Workaround new balance
            dto.setNewBalance(dto.getBalanceChange().add(
                dto.getNewBalance() != null? dto.getNewBalance() : BigDecimal.ZERO)
            );
            var statement = connection.createStatement();
            statement.executeUpdate(String.format(UPDATE_NEW_BALANCE_SQL, dto.getNewBalance(), dto.getUsername()));
            statement.close();

            results = true;
        } catch (SQLException e) {
            e.printStackTrace();
            dto.setErrorCode(e.getMessage());
        }
        return results;
    }

    protected void createTable() {
        try (var connection =jdbcPool.getConnection()) {
            var createStatement = connection.createStatement();
            createStatement.executeUpdate(TABLE_DROP_SQL);
            createStatement.executeUpdate(TABLE_CREATION_SQL);
            createStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
