public class Config {

    /**
     * Server port
     */
    public final static int serverPort = 8888;
    /**
     * Pool size
     */
    public final static int dbMaxPoolSize = 200;
    /**
     * Database URL
     */
    public final static String dbURL = "jdbc:hsqldb:mem:playtech_db;sql.syntax_pgs=true";
    /**
     * Database user
     */
    public final static String dbUer = "sa";
    /**
     * Database password
     */
    public final static String dbPassword = "";
    /**
     * Statistics print delay
     */
    public final static int statisticsPrintDelay = 60000;

}
