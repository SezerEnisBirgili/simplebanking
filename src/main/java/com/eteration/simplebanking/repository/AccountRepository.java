package com.eteration.simplebanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eteration.simplebanking.model.Account;

public interface AccountRepository extends JpaRepository<Account, String>{
    Optional<Account> findByAccountNumber(String accountNumber);
}
