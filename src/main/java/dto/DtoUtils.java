package dto;

import java.math.BigDecimal;

public class DtoUtils {

    private static final String DELIMITER = ";";

    public static PlayerDTO parseRequest(final String clientRequest)  {
        var vals = clientRequest.split(DELIMITER);
        var playerDTO = new PlayerDTO(
                vals[0], vals[1], new BigDecimal(vals[2])
        );
        return playerDTO;
    }


    public static String createResponse(final PlayerDTO playerDTO) {
        return String.join(DELIMITER,
                playerDTO.getTransactionId(),
                playerDTO.getErrorCode(),
                ""+playerDTO.getBalanceVersion(),
                ""+playerDTO.getBalanceChange(),
                ""+playerDTO.getNewBalance()
        );
    }

}
