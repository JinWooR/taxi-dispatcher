package com.taxidispatcher.modules.account.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.CredentialJpaEntity;
import com.taxidispatcher.modules.account.domain.aggregate.Account;
import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.Version;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final CredentialMapper credentialMapper;

    public AccountJpaEntity toJpa(Account account) {
        AccountJpaEntity accountJpaEntity = new AccountJpaEntity(account.getAccountId().value(), account.getStatus(), account.getVersion().getValue());

        account.credentialsView().stream()
                .map(credentialMapper::toJpa)
                .forEach(accountJpaEntity::addCredential);

        return accountJpaEntity;
    }

    @Deprecated
    public Account toDomain(AccountJpaEntity accountJpaEntity) {
        return Account.of(new AccountId(accountJpaEntity.getId()), accountJpaEntity.getStatus(), Version.of(accountJpaEntity.getVersion()));
    }

    public Account toDomain(AccountJpaEntity accountJpaEntity, List<CredentialJpaEntity> credentialJpaEntities) {
        Account account = Account.of(new AccountId(accountJpaEntity.getId()), accountJpaEntity.getStatus(), Version.of(accountJpaEntity.getVersion()));

        credentialJpaEntities.stream()
                .map(credentialMapper::toDomain)
                .forEach(account::addCredential);

        return account;
    }

}
