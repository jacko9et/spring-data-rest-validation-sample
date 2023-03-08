package com.example.spring.data.rest.validation.sample.domain;

import com.example.spring.data.rest.validation.sample.validation.RepositoryEventValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Component
public class AccountValidator implements RepositoryEventValidator {

    private final LocalValidatorFactoryBean defaultValidate;
    private final AccountRepository accountRepository;

    public AccountValidator(LocalValidatorFactoryBean defaultValidate,
                            AccountRepository accountRepository) {
        this.defaultValidate = defaultValidate;
        this.accountRepository = accountRepository;
    }


    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        switch (account.getType()) {
            case "email" -> defaultValidate.validate(account, errors, Email.class);
            case "phone" -> defaultValidate.validate(account, errors, Phone.class);
            case "username" -> defaultValidate.validate(account, errors, Username.class);
            default -> errors.rejectValue("type", "validation.NotSupportType");
        }
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

    public interface Username {
    }

    public interface Email {
    }

    public interface Phone {
    }

}
