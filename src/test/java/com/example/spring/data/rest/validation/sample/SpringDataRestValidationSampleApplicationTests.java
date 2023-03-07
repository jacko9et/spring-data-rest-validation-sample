package com.example.spring.data.rest.validation.sample;

import com.example.spring.data.rest.validation.sample.domain.Account;
import com.example.spring.data.rest.validation.sample.domain.AccountRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringDataRestValidationSampleApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void contextLoads() {
        Account account = new Account();
        account.setAlias("Tester");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/accounts", account, String.class);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        Optional<Account> optionalAccount =
                accountRepository.findAccountByAlias("Tester", Account.class);
        assertThat(optionalAccount.orElse(null)).isNotNull();

        Account repeat = new Account();
        repeat.setAlias("Tester");
        responseEntity =
                restTemplate.postForEntity("/accounts", repeat, String.class);
        assertThat(responseEntity.getStatusCode().is4xxClientError()).isTrue();
        assertThat(JsonPath.parse(responseEntity.getBody()).read("$.errors[0].message")
                .equals("Tester already exists")).isTrue();
    }

}
