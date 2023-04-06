package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int accountTo;
    private int accountFrom;
    private BigDecimal amount;
    private int transferTypeId;
    private String userTo;
    private String userFrom;

    public Transfer(){
    }

    public Transfer(int transferId, int accountTo, int accountFrom, BigDecimal amount, int transferTypeId, String userTo, String userFrom) {
        this.transferId = transferId;
        this.accountTo = accountTo;
        this.accountFrom = accountFrom;
        this.amount = amount;
        this.transferTypeId = transferTypeId;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", accountTo=" + accountTo +
                ", accountFrom=" + accountFrom +
                ", amount=" + amount +
                ", transferTypeId=" + transferTypeId +
                ", userTo='" + userTo + '\'' +
                ", userFrom='" + userFrom + '\'' +
                '}';
    }
}
