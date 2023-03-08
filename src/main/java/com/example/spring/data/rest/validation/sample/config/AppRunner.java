package com.example.spring.data.rest.validation.sample.config;

import com.example.spring.data.rest.validation.sample.domain.Account;
import com.example.spring.data.rest.validation.sample.domain.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {
    private final AccountRepository accountRepository;

    public AppRunner(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Account account = new Account();
        account.setAlias("admin");
        accountRepository.save(account);
    }
}
