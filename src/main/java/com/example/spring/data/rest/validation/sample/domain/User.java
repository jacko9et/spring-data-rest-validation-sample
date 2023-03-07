package com.example.spring.data.rest.validation.sample.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends AbstractEntity {

    @NotBlank
    private String nickname;

}
