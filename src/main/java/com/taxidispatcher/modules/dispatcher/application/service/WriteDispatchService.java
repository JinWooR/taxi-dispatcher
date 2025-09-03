package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.application.port.in.WriteDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.WriteDispatchCommand;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class WriteDispatchService implements WriteDispatchAdapter {
    private final DispatchRepository dispatchRepository;
    private final Clock clock = Clock.systemUTC();

    @Override
    public Dispatch handle(WriteDispatchCommand command) {
        if (dispatchRepository.existsByUserIdDispatch(command.userId())) {
            throw new AppException(ErrorCode.CONFLICT, "완료되지 않은 배차 요청이 존재합니다.");
        }

        DispatchId dispatchId = DispatchId.newId();

        int retryCnt = 1;
        do {
            if (dispatchRepository.existsById(dispatchId)) {
                dispatchId = DispatchId.newId();
                retryCnt++;
            } else {
                break;
            }
        } while (retryCnt < 5);

        Dispatch dispatch = Dispatch.createNew(
                dispatchId,
                command.userId(),
                command.startAddr(),
                command.arrivalAddr(),
                Instant.now(clock)
        );

        dispatchRepository.save(dispatch);

        return dispatch;
    }
}
