package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ServiceTests {
    
    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void givenAccountNumber_FindAccount_thenReturnAccount() throws Exception {
        Account account = new Account("Ali Veli", "1234");
        when(repository.findByAccountNumber("1234")).thenReturn(Optional.of(account));
        Account found = accountService.findAccount("1234");
        assertEquals(account, found);
    }

    @Test
    public void givenAccountNumber_FindAccount_thenThrowAccountNotFoundException() {
        when(repository.findByAccountNumber("9999")).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.findAccount("9999"));
    }

    @Test
    public void givenAccountNumberAndDepositTransaction_Credit_thenReturnJson() throws Exception {
        Account account = new Account("Ali Veli", "1234");
        DepositTransaction tx = new DepositTransaction(100);

        when(repository.findByAccountNumber("1234")).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        TransactionStatus status = accountService.credit("1234", tx);

        assertEquals("OK", status.getStatus());
        assertEquals(tx.getApprovalCode(), status.getApprovalCode());
    }

    @Test
    public void givenAccountNumberAndDepositTransaction_Credit_thenThrowAccountNotFoundException() {
        DepositTransaction tx = new DepositTransaction(100);
        when(repository.findByAccountNumber("9999")).thenReturn(Optional.empty());
        
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> accountService.credit("9999", tx));
        
        assertEquals(HttpStatus.NOT_FOUND.value(), ex.getStatus().value());
        assertEquals("ACCOUNT_NOT_FOUND", ex.getReason());
    }

    @Test
    public void givenAccountNumberAndWithdrawalTransaction_Debit_thenReturnJson() throws Exception {
        Account account = new Account("Ali Veli", "1234");
        account.deposit(200);
        WithdrawalTransaction tx = new WithdrawalTransaction(50);

        when(repository.findByAccountNumber("1234")).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        TransactionStatus status = accountService.debit("1234", tx);

        assertEquals("OK", status.getStatus());
        assertEquals(tx.getApprovalCode(), status.getApprovalCode());
    }

    @Test
    public void givenAccountNumberAndWithdrawalTransaction_Debit_thenThrowInsufficientBalanceException() throws Exception {
        Account account = new Account("Ali Veli", "1234");
        WithdrawalTransaction tx = new WithdrawalTransaction(100);

        when(repository.findByAccountNumber("1234")).thenReturn(Optional.of(account));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> accountService.debit("1234", tx));
       
        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getStatus().value());
        assertEquals("INSUFFICIENT_BALANCE", ex.getReason());
    }

    @Test
    public void givenAccountNumberAndWithdrawalTransaction_Debit_thenThrowAccountNotFoundException() {
        WithdrawalTransaction tx = new WithdrawalTransaction(100);
        when(repository.findByAccountNumber("9999")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> accountService.debit("9999", tx));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("ACCOUNT_NOT_FOUND", ex.getReason());
    }


}
