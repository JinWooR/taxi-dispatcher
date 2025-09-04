package com.taxidispatcher.modules.dispatcher.application.service;

import com.taxidispatcher.modules.dispatcher.adapter.client.dto.CandidateDriverRequest;
import com.taxidispatcher.modules.dispatcher.application.port.in.FindDispatchCandidateDriverAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchCandidateDriverRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.DispatchRepository;
import com.taxidispatcher.modules.dispatcher.application.port.out.FindDispatchCandidateDriverClient;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.Dispatch;
import com.taxidispatcher.modules.dispatcher.domain.aggregate.DispatchCandidateDriver;
import com.taxidispatcher.modules.dispatcher.domain.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FindDispatchCandidateDriverService implements FindDispatchCandidateDriverAdapter {
    private final DispatchRepository dispatchRepository;
    private final DispatchCandidateDriverRepository dispatchCandidateDriverRepository;
    private final FindDispatchCandidateDriverClient driverClient;
    private final Clock clock = Clock.systemUTC();

    @Override
    public void handle(DispatchId dispatchId) {
        var dispatch = dispatchRepository.findById(dispatchId)
                .orElseThrow(() -> new IllegalArgumentException("배차 정보 확인 불가"));

        if (!dispatch.getStatus().equals(DispatchStatus.REQUEST)) {
            throw new IllegalStateException("배차 요청 상태가 아닙니다. (현재 상태: " + dispatch.getStatus() + ")");
        } else if (dispatch.getAround().equals(DispatchAroundMeter.END)) {
            throw new IllegalStateException("이미 배차 후보 기사 요청 시간 초과되었습니다.");
        }

        List<DispatchCandidateDriver> candidateDrivers = dispatchCandidateDriverRepository.findByDrivers(dispatchId);
        if (dispatch.nextAround().equals(DispatchAroundMeter.END)) {
            findCandidateTimeOut(dispatch, candidateDrivers);
        } else {
            findCandidateNext(dispatch, candidateDrivers);
        }
    }

    private void findCandidateNext(Dispatch dispatch, List<DispatchCandidateDriver> candidateDrivers) {
        List<UUID> driverIdList = candidateDrivers.stream().map(candidateDriver -> candidateDriver.getId().driverId()).toList();
        var dispatchStartAddr = dispatch.getStartAddr();
        var candidateRequest = new CandidateDriverRequest(driverIdList, dispatchStartAddr.x(), dispatchStartAddr.y(), dispatch.nextAround().getMeters());

        List<DispatchCandidateDriver> newCandidateDrivers = driverClient.callDrivers(candidateRequest)
                .stream().map(driverId -> {
                    var candidateDriverId = new CandidateDriverId(dispatch.getId(), driverId);
                    return DispatchCandidateDriver.createNew(candidateDriverId);
                }).toList();

        Instant timeOut = Instant.now(clock)
                .plusSeconds(newCandidateDrivers.isEmpty() ? 0 : 60);

        dispatchCandidateDriverRepository.saveAll(newCandidateDrivers);

        dispatch.nextAround(timeOut);

        dispatchRepository.save(dispatch);
        
        // TODO. newCandidateDrivers 신규 후보 택시 기사들에게 배차 요청서 도착 알림 발송
    }

    private void findCandidateTimeOut(Dispatch dispatch, List<DispatchCandidateDriver> candidateDrivers) {
        candidateDrivers.stream().filter(candidateDriver -> candidateDriver.getStatus().equals(CandidateStatus.REQUEST))
                .forEach(candidateDriver -> candidateDriver.updateStatus(CandidateStatus.TIME_OUT));

        // 시간 초과
        dispatch.nextAround(Instant.now());

        dispatchCandidateDriverRepository.saveAll(candidateDrivers);
        dispatchRepository.save(dispatch);
    }
}
