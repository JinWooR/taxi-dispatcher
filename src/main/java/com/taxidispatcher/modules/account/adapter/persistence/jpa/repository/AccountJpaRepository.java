package com.taxidispatcher.modules.account.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.account.adapter.persistence.jpa.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, UUID> {

    @Query("""
        select a
        from AccountJpaEntity a
        left join fetch a.credentials c
        where a.id = :uuid and (c.delYn = 'N' or c.delYn is null)
    """)
    @Override
    Optional<AccountJpaEntity> findById(UUID uuid);
}
