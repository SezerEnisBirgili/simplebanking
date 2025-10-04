package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.CreditRequest;
import com.eteration.simplebanking.dto.DebitRequest;
import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.mapper.Mapper;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.AccountNotFoundException;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;
 
    @Mock
    private AccountService service;

    @Test
    void givenId_GetAccount_thenReturnJson() throws Exception {
        String accountNumber = "17892";
        Account account = new Account("Kerem Karaca", accountNumber);
        AccountDTO accountDTO = Mapper.toAccountDTO(account);

        when(service.findAccount(accountNumber)).thenReturn(account);

        ResponseEntity<AccountDTO> result = controller.getAccount(accountNumber);

        assertNotNull(result);
        assertNotNull(result.getBody());
        AccountDTO body = result.getBody();
        assertEquals(accountDTO.getAccountNumber(), body.getAccountNumber());
        assertEquals(accountDTO.getOwner(), body.getOwner());
        assertEquals(accountDTO.getBalance(), body.getBalance());
        assertEquals(accountDTO.getTransactions(), body.getTransactions());
    }

    @Test
    void givenIdAndAmount_Debit_thenReturnJson() throws Exception {
        String accountNumber = "17892";
        DebitRequest request = new DebitRequest();
        request.setAmount(100.0);

        TransactionStatus status = new TransactionStatus("OK", "APPROVAL456");

        when(service.debit(eq(accountNumber), any(WithdrawalTransaction.class))).thenReturn(status);

        ResponseEntity<TransactionStatus> result = controller.debit(accountNumber, request);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("APPROVAL456", result.getBody().getApprovalCode());
    }

    @Test
    void givenIdAndAmount_Credit_thenReturnJson() throws Exception {
        String accountNumber = "17892";
        CreditRequest request = new CreditRequest();
        request.setAmount(100.0);

        TransactionStatus status = new TransactionStatus("OK", "APPROVAL456");

        when(service.credit(eq(accountNumber), any(DepositTransaction.class))).thenReturn(status);

        ResponseEntity<TransactionStatus> result = controller.credit(accountNumber, request);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("OK", result.getBody().getStatus());
        assertEquals("APPROVAL456", result.getBody().getApprovalCode());
    }

      
    @Test
    void givenIdAndAmount_DebitExceed_thenThrowResponseStatusException() 
        throws AccountNotFoundException, InsufficientBalanceException {
        String accountNumber = "17892";
        DebitRequest request = new DebitRequest();
        request.setAmount(1000.0);

        when(service.debit(eq(accountNumber), any(WithdrawalTransaction.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "INSUFFICIENT_BALANCE"));

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> controller.debit(accountNumber, request)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("INSUFFICIENT_BALANCE", ex.getReason());
    }

    @Test
    void givenIdAndAmount_DebitAccountNotFound_thenThrowResponseStatusException() 
        throws InsufficientBalanceException, AccountNotFoundException {
        String accountNumber = "99999";
        DebitRequest request = new DebitRequest();
        request.setAmount(1000.0);

        when(service.debit(eq(accountNumber), any(WithdrawalTransaction.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND"));

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> controller.debit(accountNumber, request)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("ACCOUNT_NOT_FOUND", ex.getReason());
    }

    @Test
    void givenIdAndAmount_CreditAccountNotFound_thenThrowResponseStatusException() 
        throws InsufficientBalanceException, AccountNotFoundException {
        String accountNumber = "99999";
        CreditRequest request = new CreditRequest();
        request.setAmount(100.0);

        when(service.credit(eq(accountNumber), any(DepositTransaction.class)))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "ACCOUNT_NOT_FOUND"));

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> controller.credit(accountNumber, request)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        assertEquals("ACCOUNT_NOT_FOUND", ex.getReason());
    }

    
}
