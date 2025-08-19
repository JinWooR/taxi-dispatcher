package com.taxidispatcher.modules.user.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.UserId;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UserId userId);
    Optional<User> findByAccountId(UUID accountId);
    boolean existsById(UserId userId);
    User save(User user);
    void delete(UserId userId);
}
