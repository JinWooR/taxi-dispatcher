package com.taxidispatcher.modules.user.adapter.persistence.jpa.entity;

import com.taxidispatcher.modules.user.domain.model.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Table(name = "USERS")
@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = """
    update USERS
    set user_status = 'DELETED'
    where user_status = 'ACTIVE' and id = ?
    """)
@SQLRestriction("user_status = 'ACTIVE'")
public class UserJpaEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID accountId;

    @Column(nullable = false)
    private String name;

    // 도시 정보
    @Column(nullable = false)
    private String sd;
    @Column(nullable = false)
    private String sgg;
    @Column(nullable = false)
    private String emd;

    // 주소 정보
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String addressDetail;

    @CreatedDate
    @Column(nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column
    private Instant updateAt;

    public UserJpaEntity(UUID id, UserStatus userStatus, UUID accountId, String name, String sd, String sgg, String emd, String address, String addressDetail) {
        this.id = id;
        this.userStatus = userStatus;
        this.accountId = accountId;
        this.name = name;
        this.sd = sd;
        this.sgg = sgg;
        this.emd = emd;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
