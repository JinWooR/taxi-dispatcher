package com.taxidispatcher.modules.user.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.user.adapter.persistence.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByAccountId(UUID accountId);
}
