package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private AccountDao accountDao;
    private TransferDao transferDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, TransferDao transferDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }


    @RequestMapping(value = "/accounts/balance/{username}", method = RequestMethod.GET)
    public BigDecimal findAccountBalance (@PathVariable String username) {
        return accountDao.findAccountBalance(username);             //works
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'CREATOR', 'USER')")
//    @RequestMapping(value = "/accounts/balance", method = RequestMethod.PUT)
//    public void updateBalance(BigDecimal amount, String userTo, String userFrom) {
//        accountDao.updateBalance(amount, userTo, userFrom);
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CREATOR')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping (value = "/accounts", method = RequestMethod.POST)
    public void create(@Valid @RequestBody Account account, String username) {
        accountDao.create(account, username);
    }    // stuck here should it be a boolean or void or something else


    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String username) {
        accountDao.delete(username);
    }


    @RequestMapping(value = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer transferById(@PathVariable int transferId) {
        return transferDao.transferById(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
    public Transfer moneyTransfer(@RequestBody Transfer transfer) {
        Transfer newTransfer = null;
        if (!transfer.getUserFrom().equals(transfer.getUserTo())){
            if(!transfer.getAmount().equals(new BigDecimal(0))){
                if(accountDao.findAccountBalance(transfer.getUserFrom()).compareTo(transfer.getAmount()) >= 0){
                    int transferId = transferDao.moneyTransfer(transfer.getUserFrom(), transfer.getUserTo(), transfer.getAmount(), transfer.getTransferTypeId());
                    if (transferId != -1){
                        //                   accountDao.updateBalance(accountDao.getAccount(userFrom), userFrom);
                        accountDao.updateBalance(transfer.getAmount(), transfer.getUserTo(), transfer.getUserFrom());
                        newTransfer = transferDao.transferById(transferId);
                    }
                }
            }
        }
            return newTransfer;
    }

    @RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public List<Transfer> userTransfers(Principal principal) {
        return transferDao.userTransfers(principal.getName());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> users() {
        return userDao.findAll();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable Principal principal){
        return userDao.findByUsername(principal.getName());
    }

    @RequestMapping(value = "/users/{username}/id", method = RequestMethod.GET)
    public int findIdByUsername(@PathVariable Principal principal){
        return userDao.findIdByUsername(principal.getName());
    }

}
