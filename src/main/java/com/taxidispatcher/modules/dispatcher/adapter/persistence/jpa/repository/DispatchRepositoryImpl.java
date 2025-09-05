package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.repository;

import com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa.mapper.DispatchMapper;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DispatchRepositoryImpl implements DispatchRepository {
    private final DispatchJpaRepository dispatchJpaRepository;
    private final DispatchMapper dispatchMapper;

    @Override
    public Optional<Dispatch> findById(DispatchId dispatchId) {
        var dispatch = dispatchJpaRepository.findById(dispatchId.id())
                .map(dispatchMapper::toDomain)
                .orElse(null);

        return Optional.ofNullable(dispatch);
    }

    @Override
    public boolean existsById(DispatchId dispatchId) {
        return dispatchJpaRepository.findById(dispatchId.id()).isPresent();
    }

    @Override
    public Dispatch save(Dispatch dispatch) {
        var entity = dispatchMapper.toJpa(dispatch);

        entity = dispatchJpaRepository.save(entity);

        return dispatchMapper.toDomain(entity);
    }

    @Override
    public boolean existsByUserIdDispatch(UUID uuid) {
        return dispatchJpaRepository.findOneUserIdAndStatus(uuid, DispatchStatus.REQUEST).isPresent();
    }
}
