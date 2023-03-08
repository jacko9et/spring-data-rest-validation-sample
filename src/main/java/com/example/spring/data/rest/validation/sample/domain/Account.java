package com.example.spring.data.rest.validation.sample.domain;

import com.example.spring.data.rest.validation.sample.validation.groups.Remove;
import com.example.spring.data.rest.validation.sample.validation.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account extends AbstractEntity {

    @NotBlank(message = "{validation.NotBlank}")
    private String alias;

    @NotNull(groups = Update.class)
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @AssertTrue(groups = Remove.class)
    private boolean deleted;

    @PrePersist
    private void createDefaultUser() {
        if (null == user) {
            user = new User();
            user.setNickname(alias);
        }
    }

}
