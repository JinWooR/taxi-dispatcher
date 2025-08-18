package com.taxidispatcher.modules.account.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "BASIC_CREDENTIALS")
@Entity
@DiscriminatorValue(value = "BASIC")
@Getter
@Setter
@NoArgsConstructor
public class BasicCredentialJpaEntity extends CredentialJpaEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentifierKind identifierKind;
    @Column(nullable = false)
    private String loginId;
    @Column(length = 255)
    private String hashPassword;

    public BasicCredentialJpaEntity(UUID id, AccountJpaEntity accountJpaEntity, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt, IdentifierKind identifierKind, String loginId, String hashPassword) {
        super(id, accountJpaEntity, isLoginEnabled, createdAt, lastUsedAt);
        this.identifierKind = identifierKind;
        this.loginId = loginId;
        this.hashPassword = hashPassword;
    }
}
