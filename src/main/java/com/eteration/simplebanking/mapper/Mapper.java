package com.eteration.simplebanking.mapper;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.TransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static TransactionDTO toTransactionDTO(Transaction t) {
        return new TransactionDTO(t.getDate(), t.getAmount(), t.getClass().getSimpleName(), t.getApprovalCode());
    }

    public static AccountDTO toAccountDTO(Account a) {
        List<TransactionDTO> transactionDTOs = a.getTransactions()
                                                .stream()
                                                .map(Mapper::toTransactionDTO)
                                                .collect(Collectors.toList());
        return new AccountDTO(a.getAccountNumber(), a.getOwner(), a.getBalance(), transactionDTOs);
    }
}
