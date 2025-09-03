package com.taxidispatcher.modules.dispatcher.adapter.persistence.jpa;

import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DispatchRepositoryImpl implements DispatchRepository {
    @Override
    public Optional<Dispatch> findById(DispatchId dispatchId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(DispatchId dispatchId) {
        return false;
    }

    @Override
    public Dispatch save(Dispatch dispatch) {
        return null;
    }

    @Override
    public boolean existsByUserIdDispatch(UUID uuid) {
        return false;
    }
}
