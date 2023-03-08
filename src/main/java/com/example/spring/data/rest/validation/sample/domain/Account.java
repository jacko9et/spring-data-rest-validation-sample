package com.example.spring.data.rest.validation.sample.domain;

import com.example.spring.data.rest.validation.sample.validation.groups.Remove;
import com.example.spring.data.rest.validation.sample.validation.groups.Update;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account extends AbstractEntity {

    @NotBlank(message = "{validation.NotBlank}")
    @Size(min = 5, max = 32, groups = AccountValidator.Username.class)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_.]+$",
            groups = AccountValidator.Username.class, message = "{validation.InvalidValue}")
    @Size(min = 11, max = 11, groups = AccountValidator.Phone.class)
    @Pattern(regexp = "^1([3-9])\\d{9}$",
            groups = AccountValidator.Phone.class, message = "{validation.InvalidValue}")
    @Size(min = 6, max = 255, groups = AccountValidator.Email.class)
    @Email(groups = AccountValidator.Email.class)
    private String alias;

    @NotBlank
    @Size(min = 2, max = 255)
    private String type;

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
