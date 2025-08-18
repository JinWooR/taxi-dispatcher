package com.taxidispatcher.modules.account.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.BasicCredentialJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.CredentialJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.OAuthCredentialJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.mapper.AccountMapper;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.mapper.CredentialMapper;
import com.taxidispatcher.modules.account.application.port.out.AccountRepository;
import com.taxidispatcher.modules.account.domain.aggregate.Account;
import com.taxidispatcher.modules.account.domain.aggregate.BasicCredential;
import com.taxidispatcher.modules.account.domain.aggregate.Credential;
import com.taxidispatcher.modules.account.domain.aggregate.OAuthCredential;
import com.taxidispatcher.modules.account.domain.model.AccountStatus;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {
    private final AccountJpaRepository accountJpaRepository;
    private final BasicCredentialJpaRepository basicCredentialJpaRepository;
    private final OAuthCredentialJpaRepository oAuthCredentialJpaRepository;
    private final AccountMapper accountMapper;
    private final CredentialMapper credentialMapper;

    @Override
    public Optional<Account> findByIdentifier(LoginIdentifier loginIdentifier) {
        BasicCredentialJpaEntity basicCredentialJpaEntity = basicCredentialJpaRepository.findByIdentifierKindAndLoginId(
                loginIdentifier.identifierKind(), loginIdentifier.loginId()
        ).orElse(null);

        if (basicCredentialJpaEntity == null) {
            return Optional.empty();
        }

        AccountJpaEntity accountJpaEntity = accountJpaRepository
                .findById(basicCredentialJpaEntity.getAccountJpaEntity().getId())
                .orElse(null);

        if (accountJpaEntity == null || accountJpaEntity.getStatus() == AccountStatus.DELETED) {
            return Optional.empty();
        }

        Account account = accountMapper.toDomain(accountJpaEntity, accountJpaEntity.getCredentials());

        return Optional.of(account);
    }

    @Override
    public boolean existsByIdentifier(LoginIdentifier loginIdentifier) {
        return findByIdentifier(loginIdentifier).isPresent();
    }

    @Override
    public UUID save(Account account) {
        AccountJpaEntity accountJpaEntity = accountJpaRepository.findById(account.getAccountId().value())
                .orElse(null);

        // 엔티티가 존재하지 않는 경우 (자식 포함) 신규 등록
        if (accountJpaEntity == null) {
            accountJpaEntity = accountMapper.toJpa(account);
            accountJpaRepository.save(accountJpaEntity);

            return accountJpaEntity.getId();
        }

        // 기존 루트 엔티티 업데이트
        accountJpaEntity.setStatus(account.getStatus());

        // 기존 루트 하위 Credential 엔티티 병합
        mergeCredentials(accountJpaEntity, account);

        accountJpaRepository.save(accountJpaEntity);

        return accountJpaEntity.getId();
    }

    private void mergeCredentials(AccountJpaEntity accountJpaEntity, Account account) {
        Map<UUID, CredentialJpaEntity> cMap = accountJpaEntity.getCredentials().stream()
                .collect(Collectors.toMap(CredentialJpaEntity::getId, Function.identity()));

        for (Credential c : account.credentialsView()) {
            var existC = cMap.get(c.getId());
            if (existC == null) {
                var nc = credentialMapper.toJpa(c);
                cMap.put(nc.getId(), nc);
                accountJpaEntity.addCredential(nc);
            } else {
                if (existC instanceof BasicCredentialJpaEntity ebc
                        && c instanceof BasicCredential bc) {
                    ebc.setLoginEnabled(bc.isLoginEnabled());
                    ebc.setLastUsedAt(Instant.now());
                    ebc.setIdentifierKind(bc.getLoginIdentifier().identifierKind());
                    ebc.setLoginId(bc.getLoginIdentifier().loginId());
                    ebc.setHashPassword(bc.getHashPassword().hashPassword());
                } else if (existC instanceof OAuthCredentialJpaEntity eoc
                        && c instanceof OAuthCredential oc) {
                    eoc.setLoginEnabled(oc.isLoginEnabled());
                    eoc.setLastUsedAt(Instant.now());
                    eoc.setOAuthKind(oc.getoAuthKind());
                    eoc.setIss(oc.getIss());
                    eoc.setSub(oc.getSub());
                    eoc.setEmailLink(oc.getEmailLink());
                }
            }
        }


    }
}
