package com.taxidispatcher.modules.account.domain.model;

public record HashPassword(String hashPassword) {
    public HashPassword {
        if (hashPassword == null || hashPassword.isBlank()) {
            throw new IllegalArgumentException("hashPassword is not Empty!");
        }
    }
}
