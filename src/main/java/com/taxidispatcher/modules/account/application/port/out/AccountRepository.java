package com.taxidispatcher.modules.account.application.port.out;

import com.taxidispatcher.modules.account.domain.aggregate.Account;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> findByIdentifier(LoginIdentifier loginIdentifier);
    boolean existsByIdentifier(LoginIdentifier loginIdentifier);
    UUID save(Account account);
}
