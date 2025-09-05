package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchAddressInfoId;
import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.entity.DispatchAddressInfoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface DispatchAddressInfoJpaRepository extends JpaRepository<DispatchAddressInfoJpaEntity, DispatchAddressInfoId> {
}
