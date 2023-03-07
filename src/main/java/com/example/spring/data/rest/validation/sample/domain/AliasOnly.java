package com.example.spring.data.rest.validation.sample.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "AliasOnly", types = Account.class)
public interface AliasOnly {

    Long getId();

    String getAlias();

}
