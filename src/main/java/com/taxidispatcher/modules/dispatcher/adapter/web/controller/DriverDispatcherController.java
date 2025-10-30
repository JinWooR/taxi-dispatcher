package com.taxidispatcher.modules.dispatcher.adapter.web.controller;

import com.taxidispatcher.modules.dispatcher.application.port.in.*;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.security.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("drivers/me/dispatches")
@RestController
@RequiredArgsConstructor
@Secured("hasRole('DRIVER')")
public class DriverDispatcherController {
    private final ApprovalDispatchAdapter approvalDispatchAdapter;
    private final RefusalDispatchAdapter refusalDispatchAdapter;
    private final DispatchDrivingStartAdapter dispatchDrivingStartAdapter;
    private final DispatchDrivingArrivalAdapter dispatchDrivingArrivalAdapter;

    // 배차 요청서 정보 조회
    @GetMapping("{dispatchId}")
    public ResponseEntity<String> info(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity.ok(null);
    }

    // 배차 승인
    @PostMapping("{dispatchId}/approval")
    public ResponseEntity<String> approval(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        approvalDispatchAdapter.handle(new ApprovalDispatchCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id())));
        
        return ResponseEntity
                .ok("배차 승인");
    }

    // 배차 거절
    @PostMapping("{dispatchId}/refusal")
    public ResponseEntity<String> refusal(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        refusalDispatchAdapter.handle(new RefusalDispatchCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id())));

        return ResponseEntity
                .ok("배차 거절");
    }

    // 운행 시작
    @PostMapping("{dispatchId}/start")
    public ResponseEntity<String> start(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        dispatchDrivingStartAdapter.handle(new DispatchDrivingStartCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id())));

        return ResponseEntity
                .ok("운행 시작.");
    }

    // 목적지 도착
    @PostMapping("{dispatchId}/arrival")
    public ResponseEntity<String> arrival(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        dispatchDrivingArrivalAdapter.handle(new DispatchDrivingArrivalCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id())));

        return ResponseEntity
                .ok("운행 완료.");
    }
}
