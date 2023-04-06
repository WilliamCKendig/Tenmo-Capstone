package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer transferById (int transferId);

    int moneyTransfer(String userFrom, String userTo, BigDecimal amount, int transferTypeId);
    // seems suspicious to delete a transfer

    List<Transfer> userTransfers(String username);
}
