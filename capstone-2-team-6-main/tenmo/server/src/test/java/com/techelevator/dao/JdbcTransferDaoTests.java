package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private JdbcTransferDao sut;

    private static final Transfer TRANSFER_1 =new Transfer(3001, 2001, 2002, new BigDecimal(300), 1, "bob", "user");
    private static final Transfer TRANSFER_2 =new Transfer(3002, 2002, 2001, new BigDecimal(400), 1, "user", "bob");

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void transferById_returns_correct_transfer() {
        Transfer transfer = sut.transferById(3001);
        Assert.assertEquals(3001, transfer.getTransferId());

    }

    @Test
    public void transferById_returns_null_if_id_not_found(){
        Transfer transfer = sut.transferById(3004);
        Assert.assertNull(transfer);
    }

    @Test
    public void moneyTransfer_returns_correct_int() {
        int integer = sut.moneyTransfer("bob", "user", new BigDecimal(200), 1);
        Assert.assertEquals(1, integer);

    }

    @Test
    public void userTransfers() {
        List<Transfer> transfersList = sut.userTransfers("bob");
        Assert.assertEquals(2, transfersList.size());
    }



    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
        Assert.assertEquals(expected.getUserFrom(), actual.getUserFrom());
        Assert.assertEquals(expected.getUserTo(), actual.getUserTo());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
    }
}
