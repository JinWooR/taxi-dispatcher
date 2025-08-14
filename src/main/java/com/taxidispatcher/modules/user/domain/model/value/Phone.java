package com.taxidispatcher.modules.user.domain.model.value;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class Phone {
    private final String value;

    private Phone(String value) {
        this.value = value;
    }

    public static Phone of(@NotNull @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$") String value) {
        return new Phone(value);
    }

    public String value() {
        return value;
    }
}
