package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal findAccountBalance(String username);

    void updateBalance(BigDecimal amount, String userTo, String userFrom);

    void create(Account account, String username);

    void delete(String username);

    Account getAccount (String username);
}
