package com.taxidispatcher.modules.user.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.user.domain.aggregate.User;
import com.taxidispatcher.modules.user.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    @Override
    public Optional<User> findById(UserId userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByAccountId(UUID accountId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UserId userId) {
        return false;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void delete(UserId userId) {

    }
}
