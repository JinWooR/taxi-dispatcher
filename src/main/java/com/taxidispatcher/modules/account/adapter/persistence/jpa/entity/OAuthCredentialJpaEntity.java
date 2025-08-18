package com.taxidispatcher.modules.account.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.account.domain.model.OAuthKind;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "OAUTH_CREDENTIALS")
@Entity
@DiscriminatorValue(value = "OAUTH")
@Getter
@Setter
@NoArgsConstructor
public class OAuthCredentialJpaEntity extends CredentialJpaEntity {
    private OAuthKind oAuthKind;
    private String iss; // 발급자
    private String sub; // 발급 주체의 사용자 고유 식별자
    private String emailLink; // 이메일(표기용). 검증 및 식별에는 사용 X

    public OAuthCredentialJpaEntity(OAuthKind oAuthKind, String iss, String sub, String emailLink) {
        this.oAuthKind = oAuthKind;
        this.iss = iss;
        this.sub = sub;
        this.emailLink = emailLink;
    }

    public OAuthCredentialJpaEntity(UUID id, AccountJpaEntity accountJpaEntity, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, OAuthKind oAuthKind, String iss, String sub, String emailLink) {
        super(id, accountJpaEntity, isLoginEnabled, createdAt, lastUsedAt);
        this.oAuthKind = oAuthKind;
        this.iss = iss;
        this.sub = sub;
        this.emailLink = emailLink;
    }

    public OAuthCredentialJpaEntity(UUID id, AccountJpaEntity accountJpaEntity, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt) {
        super(id, accountJpaEntity, isLoginEnabled, createdAt, lastUsedAt);
    }
}
