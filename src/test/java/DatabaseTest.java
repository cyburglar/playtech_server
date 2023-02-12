import dto.PlayerDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest {

  @Test
  void insetOrUpdateBalance() {
    var database = new Database();

    var dto = new PlayerDTO("test_user", "test_transaction", new BigDecimal("111.00"));
    boolean isOk = database.insetOrUpdateBalance(dto);
    assertAll( "Insert",
        () -> assertTrue(isOk),
        () -> assertEquals("test_user", dto.getUsername()),
        () -> assertEquals("test_transaction", dto.getTransactionId()),
        () -> assertEquals(1,dto.getBalanceVersion()),
        () -> assertEquals(new BigDecimal("111.00"), dto.getBalanceChange()),
        () -> assertEquals(new BigDecimal("0.00"), dto.getOldBalance()),
        () -> assertEquals(new BigDecimal("111.00"), dto.getNewBalance())
    );

    dto.setBalanceChange(new BigDecimal("111.00"));
    boolean isOk2 = database.insetOrUpdateBalance(dto);
    assertAll( "update",
        () -> assertTrue(isOk2),
        () -> assertEquals(2, dto.getBalanceVersion()),
        () -> assertEquals(new BigDecimal("111.00"), dto.getOldBalance()),
        () -> assertEquals(new BigDecimal("222.00"), dto.getNewBalance())
    );


  }

}