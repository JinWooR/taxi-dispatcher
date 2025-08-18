package com.taxidispatcher.modules.account.adapter.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;
import java.util.UUID;

@Table(name = "CREDENTIALS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "cred_type", length = 16) // BASIC / OAUTH
@SQLDelete(sql = """
        UPDATE CREDENTIALS
        SET del_yn = 'Y'
        WHERE id = ?
        """) // Soft Delete
@SQLRestriction("del_yn = 'N'")
public abstract class CredentialJpaEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountJpaEntity accountJpaEntity;

    @Column(nullable = false)
    private boolean isLoginEnabled;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private Instant lastUsedAt;

    @Column(nullable = false)
    private String delYn; // Y / N

    public CredentialJpaEntity(UUID id, AccountJpaEntity accountJpaEntity, boolean isLoginEnabled, Instant createdAt, Instant lastUsedAt) {
        this.id = id;
        this.accountJpaEntity = accountJpaEntity;
        this.isLoginEnabled = isLoginEnabled;
        this.createdAt = createdAt;
        this.lastUsedAt = lastUsedAt;
    }

    public void setAccount(AccountJpaEntity accountJpaEntity) {
        this.accountJpaEntity = accountJpaEntity;
    }

    @PrePersist
    public void prePersist() {
        if (delYn == null || delYn.isBlank()) {
            delYn = "N";
        }
    }
}
