package com.taxidispatcher.modules.account.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.account.domain.model.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "ACCOUNTS")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountJpaEntity {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Version
    private long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updatedAt;

    @OneToMany(mappedBy = "accountJpaEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CredentialJpaEntity> credentials = new ArrayList<>();

    public AccountJpaEntity(UUID id, AccountStatus status, long version) {
        this.id = id;
        this.status = status;
        this.version = version;
        this.credentials = new ArrayList<>();
    }

    public void addCredential(CredentialJpaEntity nc) {
        if (this.credentials.stream()
                .anyMatch(c -> c.getId().equals(nc.getId()))) return;
        credentials.add(nc);
        nc.setAccount(this);
    }

    public void removeCredential(UUID credentialId) {
        credentials.removeIf(c -> {
            boolean isMatch = c.getId().equals(credentialId);
            if (isMatch) c.setAccount(null);
            return isMatch;
        });
    }

    public AccountJpaEntity(UUID id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
