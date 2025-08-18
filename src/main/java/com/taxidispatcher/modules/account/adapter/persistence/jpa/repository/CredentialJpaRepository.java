package com.taxidispatcher.modules.account.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.CredentialJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CredentialJpaRepository extends JpaRepository<CredentialJpaEntity, UUID> {
    List<CredentialJpaEntity> findAllByAccountJpaEntity(AccountJpaEntity accountJpaEntity);
}
