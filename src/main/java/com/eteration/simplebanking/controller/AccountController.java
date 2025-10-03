package com.eteration.simplebanking.controller;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.CreditRequest;
import com.eteration.simplebanking.dto.DebitRequest;
import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.mapper.Mapper;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(
            @PathVariable String accountNumber,
            @Valid @RequestBody CreditRequest request) throws AccountNotFoundException {

        DepositTransaction transaction = new DepositTransaction(request.getAmount());
        TransactionStatus status = service.credit(accountNumber, transaction);
        
        return ResponseEntity.ok(status);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(
            @PathVariable String accountNumber,
            @RequestBody DebitRequest request) throws InsufficientBalanceException, AccountNotFoundException {

        WithdrawalTransaction transaction = new WithdrawalTransaction(request.getAmount());
        TransactionStatus status = service.debit(accountNumber, transaction);

        return ResponseEntity.ok(status);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        return ResponseEntity.ok(Mapper.toAccountDTO(service.findAccount(accountNumber)));
    }
    
}