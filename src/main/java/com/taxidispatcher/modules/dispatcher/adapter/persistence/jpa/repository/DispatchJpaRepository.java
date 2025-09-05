package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DispatchJpaRepository extends JpaRepository<DispatchJpaEntity, UUID> {
}
