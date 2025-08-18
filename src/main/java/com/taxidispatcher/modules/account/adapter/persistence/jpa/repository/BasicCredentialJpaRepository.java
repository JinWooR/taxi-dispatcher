package com.taxidispatcher.modules.account.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.BasicCredentialJpaEntity;
import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasicCredentialJpaRepository extends JpaRepository<BasicCredentialJpaEntity, UUID> {
    Optional<BasicCredentialJpaEntity> findByIdentifierKindAndLoginId(IdentifierKind identifierKind, String loginId);
    boolean existsByIdentifierKindAndLoginId(IdentifierKind identifierKind, String loginId);

    List<BasicCredentialJpaEntity> findAllByAccountJpaEntity(AccountJpaEntity accountJpaEntity);
}
