package dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DtoUtilsTest {

    @Test
    void parseRequestTest() {

        var dto = DtoUtils.parseRequest("username;transaction_id;123.00");
        assertAll("DTO",
                () -> assertEquals(dto.getUsername(), "username"),
                () -> assertEquals(dto.getTransactionId(), "transaction_id"),
                () -> assertEquals("" + dto.getBalanceChange(), "123.00")
        );

    }

    @Test
    void createResponseTest() {

        var dto = new PlayerDTO() {{
            setTransactionId("transaction_id");
            setErrorCode("ErrorCode");
            setBalanceVersion(1);
            setBalanceChange(new BigDecimal("123.00"));
            setNewBalance(new BigDecimal("123.01"));
        }};

        Assertions.assertEquals(DtoUtils.createResponse(dto), "transaction_id;ErrorCode;1;123.00;123.01");

    }

}