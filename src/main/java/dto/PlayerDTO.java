package dto;

import java.math.BigDecimal;

public class PlayerDTO {

    private String username;
    private String transactionId;
    private int balanceVersion;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;
    private BigDecimal balanceChange;
    private String errorCode;

    public PlayerDTO() {    }

    public PlayerDTO(String username, String transactionId, BigDecimal balanceChange) {
        this.username = username;
        this.transactionId = transactionId;
        this.balanceChange = balanceChange;
    }

    public PlayerDTO(
            String username,
            String transactionId,
            int balanceVersion,
            BigDecimal oldBalance,
            BigDecimal newBalance,
            BigDecimal balanceChange
    ) {
        this.username = username;
        this.transactionId = transactionId;
        this.balanceVersion = balanceVersion;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.balanceChange = balanceChange;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getBalanceVersion() {
        return balanceVersion;
    }

    public void setBalanceVersion(int balanceVersion) {
        this.balanceVersion = balanceVersion;
    }

    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }
}
