package com.eteration.simplebanking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    CommandLineRunner initData(AccountRepository accountRepo) {
        return args -> {
            Account a1 = new Account("Kerem Karaca", "17892");
            Account a2 = new Account("Demet Demircan", "9834");
            accountRepo.save(a1);
            accountRepo.save(a2);
        };
    }

}
