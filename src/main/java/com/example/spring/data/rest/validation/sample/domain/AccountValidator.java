package com.example.spring.data.rest.validation.sample.domain;

import com.example.spring.data.rest.validation.sample.validation.RepositoryEventValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

@Component
public class AccountValidator implements RepositoryEventValidator {

    private final AccountRepository accountRepository;

    public AccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        if (StringUtils.hasText(account.getAlias())) {
            accountRepository.findAccountByAlias(account.getAlias(), AliasOnly.class)
                    .ifPresent(aliasOnly -> {
                        if (null == account.getId() || !account.getId().equals(aliasOnly.getId())) {
                            errors.rejectValue("alias", "validation.Unique");
                        }
                    });
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.isAssignableFrom(clazz);
    }

}
