package com.taxidispatcher.modules.account.application.port.out;

public interface PasswordHasher {
    String hash(String rawPassword);

    boolean matches(String hash, String rawPassword);
}
