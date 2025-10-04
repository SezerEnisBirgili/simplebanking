package com.eteration.simplebanking.services;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.AccountNotFoundException;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;

// This class is a place holder you can change the complete implementation
@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return repository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Transactional
    public TransactionStatus credit(String accountNumber, DepositTransaction tx) throws AccountNotFoundException {
        try {
            Account account = findAccount(accountNumber);
            account.post(tx);
            repository.save(account);
            return new TransactionStatus("OK", tx.getApprovalCode());
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Transactional
    public TransactionStatus debit(
        String accountNumber, 
        WithdrawalTransaction tx) throws InsufficientBalanceException, AccountNotFoundException {
        try {
            Account account = findAccount(accountNumber);
            account.post(tx);
            repository.save(account);
            return new TransactionStatus("OK", tx.getApprovalCode());
        } catch (InsufficientBalanceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT_BALANCE");
        } catch (AccountNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR", e);
        }
}
}
