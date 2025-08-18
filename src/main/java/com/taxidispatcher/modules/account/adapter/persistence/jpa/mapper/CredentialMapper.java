package com.taxidispatcher.modules.account.adapter.persistence.jpa.mapper;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.BasicCredentialJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.CredentialJpaEntity;
import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.OAuthCredentialJpaEntity;
import com.taxidispatcher.modules.account.domain.aggregate.BasicCredential;
import com.taxidispatcher.modules.account.domain.aggregate.Credential;
import com.taxidispatcher.modules.account.domain.aggregate.OAuthCredential;
import com.taxidispatcher.modules.account.domain.model.AccountId;
import com.taxidispatcher.modules.account.domain.model.HashPassword;
import com.taxidispatcher.modules.account.domain.model.LoginIdentifier;
import org.springframework.stereotype.Component;

@Component
public class CredentialMapper {

    public CredentialJpaEntity toJpa(Credential credential) {
        return toJpa(credential, null);
    }

    public CredentialJpaEntity toJpa(Credential credential, AccountJpaEntity accountJpaEntity) {
        if (credential instanceof BasicCredential bc) {
            return new BasicCredentialJpaEntity(bc.getId(), accountJpaEntity, bc.isLoginEnabled(), bc.getCreatedAt(), bc.getLastUsedAt(), bc.getLoginIdentifier().identifierKind(), bc.getLoginIdentifier().loginId(), bc.getHashPassword().hashPassword());
        } else if (credential instanceof OAuthCredential oc) {
            return new OAuthCredentialJpaEntity(oc.getId(), accountJpaEntity, oc.isLoginEnabled(), oc.getCreatedAt(), oc.getLastUsedAt());
        } else {
            throw new IllegalArgumentException("Do not supported Class. className : " + credential.getClass().getName());
        }
    }

    public Credential toDomain(CredentialJpaEntity credentialJpaEntity) {
        if (credentialJpaEntity instanceof BasicCredentialJpaEntity bce) {
            return toDomain(bce);
        } else if (credentialJpaEntity instanceof OAuthCredentialJpaEntity oce) {
            return toDomain(oce);
        } else {
            throw new IllegalArgumentException("Do not supported Class. className : " + credentialJpaEntity.getClass().getName());
        }
    }

    public Credential toDomain(BasicCredentialJpaEntity basicCredentialJpaEntity) {
        return BasicCredential.of(basicCredentialJpaEntity.getId(), new AccountId(basicCredentialJpaEntity.getAccountJpaEntity().getId()), basicCredentialJpaEntity.isLoginEnabled(), basicCredentialJpaEntity.getCreatedAt(), basicCredentialJpaEntity.getLastUsedAt(), LoginIdentifier.of(basicCredentialJpaEntity.getIdentifierKind(), basicCredentialJpaEntity.getLoginId()), new HashPassword(basicCredentialJpaEntity.getHashPassword()));
    }

    public Credential toDomain(OAuthCredentialJpaEntity oAuthCredentialJpaEntity) {
        return OAuthCredential.of(oAuthCredentialJpaEntity.getId(), new AccountId(oAuthCredentialJpaEntity.getAccountJpaEntity().getId()), oAuthCredentialJpaEntity.isLoginEnabled(), oAuthCredentialJpaEntity.getCreatedAt(), oAuthCredentialJpaEntity.getLastUsedAt(), oAuthCredentialJpaEntity.getOAuthKind(), oAuthCredentialJpaEntity.getIss(), oAuthCredentialJpaEntity.getSub(), oAuthCredentialJpaEntity.getEmailLink());
    }
}
