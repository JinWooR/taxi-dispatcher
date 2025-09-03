package com.taxidispatcher.modules.dispatcher.application.port.out;

import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;

import java.util.Optional;
import java.util.UUID;

public interface DispatchRepository {
    Optional<Dispatch> findById(DispatchId dispatchId);
    boolean existsById(DispatchId dispatchId);
    Dispatch save(Dispatch dispatch);
    boolean existsByUserIdDispatch(UUID uuid);
}
