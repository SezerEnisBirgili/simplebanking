package com.eteration.simplebanking;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModelTest {
	
	@Test
	public void givenOwnerAndAccountNumber_createAccount_thenFieldsAreSetAndBalanceIsZero() { 
		String owner = "Kerem Karaca";
		String accountNumber = "17892";
		Account account = new Account(owner, accountNumber);

		assertTrue(account.getOwner().equals("Kerem Karaca"));
		assertTrue(account.getAccountNumber().equals("17892"));
		assertTrue(account.getBalance() == 0);
	}

	@Test
	public void givenAmount_Deposit_thenBalanceIncreasesBy100() {
		Account account = new Account("Demet Demircan", "9834");
		account.deposit(100);
		assertTrue(account.getBalance() == 100);
	}

	@Test
    void givenNegativeAmount_Deposit_thenThrowIllegalArgumentException() {
        Account account = new Account("Demet Demircan", "9834");
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> account.deposit(-50)
        );
        assertEquals("Amount must be non-negative", ex.getMessage());
    }

	@Test
    void givenNegativeAmount_Withdraw_thenThrowIllegalArgumentException() {
        Account account = new Account("Demet Demircan", "9834");
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> account.withdraw(-50)
        );
        assertEquals("Amount must be non-negative", ex.getMessage());
    }

	@Test
	public void givenAmount_DepositAndWithdraw_thenBalanceIncreasesBy50() throws InsufficientBalanceException {
		Account account = new Account("Demet Demircan", "9834");
		account.deposit(100);
		assertTrue(account.getBalance() == 100);
		account.withdraw(50);
		assertTrue(account.getBalance() == 50);
	}

	@Test
	public void givenAccount_WithdrawExceed_thenThrowInsufficientBalanceException() {
		Assertions.assertThrows( InsufficientBalanceException.class, () -> {
			Account account = new Account("Demet Demircan", "9834");
			account.deposit(100);
			account.withdraw(500);
		  });

	}
	
	@Test
	public void givenAccount_DepositAndWithdrawal_thenBalanceAndTransactionsAreUpdated() throws InsufficientBalanceException {

		Account account = new Account("Canan Kaya", "1234");
		assertTrue(account.getTransactions().size() == 0);

		DepositTransaction depositTrx = new DepositTransaction(100);
		assertTrue(depositTrx.getDate() != null);
		account.post(depositTrx);
		assertTrue(account.getBalance() == 100);
		assertTrue(account.getTransactions().size() == 1);

		WithdrawalTransaction withdrawalTrx = new WithdrawalTransaction(60);
		assertTrue(withdrawalTrx.getDate() != null);
		account.post(withdrawalTrx);
		assertTrue(account.getBalance() == 40);
		assertTrue(account.getTransactions().size() == 2);
	}


}
