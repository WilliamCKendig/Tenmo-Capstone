package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal findAccountBalance(String username) {
        String sql = "SELECT balance FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?;";
        BigDecimal newId = jdbcTemplate.queryForObject(sql, BigDecimal.class, username);
        if (newId != null) {
            return newId;
        } else {
            return new BigDecimal(-1); //what is this doing
        }
    }

//    @Override
//    public void updateBalance(Account account, String username) {
//        String sql = "UPDATE account SET balance = ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?);";
//        jdbcTemplate.update(sql, account.getBalance(), username); //does this work for deposits and withdraws
//    }

    @Override
    public void updateBalance(BigDecimal amount, String userTo, String userFrom) {
        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?); " +
                "UPDATE account SET balance = balance - ? WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?);";
        jdbcTemplate.update(sql, amount, userTo, amount, userFrom ); //does this work for deposits and withdraws
    }

    @Override
    public void create(Account account, String username) {
        String sql = "INSERT INTO account (account_id, user_id, balance) VALUES (DEFAULT, (SELECT user_id FROM tenmo_user WHERE username = ?), 0);";
        jdbcTemplate.queryForObject(sql, Integer.class, username);

    }

    @Override
    public void delete(String username) {
        String sql = "DELETE FROM account WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?);";
        jdbcTemplate.update(sql, username);
    }

    public Account getAccount(String username){
        String sql = "SELECT account_id, tenmo_user.user_id, balance FROM account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id" +
                "WHERE username = ?;";
        Account account = null;
        account = jdbcTemplate.queryForObject(sql, Account.class, username);
        return account;
    }

    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setBalance(result.getBigDecimal("balance"));
        account.setId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        return account;
    }
}
