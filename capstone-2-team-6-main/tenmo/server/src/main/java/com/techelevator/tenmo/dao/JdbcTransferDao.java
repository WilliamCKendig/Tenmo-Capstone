package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{


    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer transferById(int transferId) {
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, tenmo_user.username, transfer.amount, transfer_type_id, account_from, account_to, user_from, user_to " +
                "FROM transfer " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
//                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE transfer_id = ?;";
        //Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if(result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public int moneyTransfer(String userFrom, String userTo, BigDecimal amount, int transferTypeId) {
        String sql = "INSERT INTO transfer (transfer_id, account_from, account_to, amount, transfer_type_id, user_from, user_to) " +
                "VALUES (DEFAULT, (SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?), " +
                "(SELECT account_id FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?), ?, ?, ?, ?) RETURNING transfer_id;";
        Integer newTransferId;  //originally had accountTo and accountFrom, changed to userTo and userFrom
        try{
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, userFrom, userTo, amount, transferTypeId, userFrom, userTo);
            return newTransferId;
        } catch (DataAccessException ex) {
            return -1;
        }
    }

    @Override
    public List<Transfer> userTransfers(String username) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, amount, user_from, transfer_type_id, user_to, account_to, account_from " +
                "FROM transfer JOIN account ON account.account_id = transfer.account_from " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE user_from = ? OR user_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username, username);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setAmount(result.getBigDecimal("amount"));
        transfer.setTransferTypeId(result.getInt("transfer_type_id"));
        transfer.setUserFrom(result.getString("user_from"));
        transfer.setUserTo(result.getString("user_to"));
        return transfer;

    }
}
