package com.taxidispatcher.modules.account.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.OAuthCredentialJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OAuthCredentialJpaRepository extends JpaRepository<OAuthCredentialJpaEntity, UUID> {
    List<OAuthCredentialJpaEntity> findAllByAccountJpaEntity(AccountJpaEntity accountJpaEntity);
}
