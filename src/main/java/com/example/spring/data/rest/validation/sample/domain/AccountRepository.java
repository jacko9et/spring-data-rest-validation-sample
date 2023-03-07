package com.example.spring.data.rest.validation.sample.domain;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface AccountRepository extends JpaRepository<Account, Long> {

    @RestResource(exported = false)
    <T> Optional<T> findAccountByAlias(@NotBlank String alias, @NonNull Class<T> type);

}