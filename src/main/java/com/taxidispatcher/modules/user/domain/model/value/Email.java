package com.taxidispatcher.modules.user.domain.model.value;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Email {
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(@NotNull @jakarta.validation.constraints.Email String value) {
        return new Email(value);
    }

    public String value() {
        return value;
    }
}
