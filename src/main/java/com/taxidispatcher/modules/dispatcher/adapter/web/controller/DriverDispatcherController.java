package com.taxidispatcher.modules.dispatcher.adapter.web.controller;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchInfoResponse;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchListResponse;
import com.taxidispatcher.modules.dispatcher.application.port.in.*;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.security.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("drivers/me/dispatches")
@RestController
@RequiredArgsConstructor
@Secured("hasRole('DRIVER')")
public class DriverDispatcherController {
    private final ViewDispatchListUseCase viewDispatchListUseCase;
    private final ViewDispatchInfoAdapter viewDispatchInfoAdapter;

    private final ApprovalDispatchAdapter approvalDispatchAdapter;
    private final RefusalDispatchAdapter refusalDispatchAdapter;
    private final DispatchDrivingStartAdapter dispatchDrivingStartAdapter;
    private final DispatchDrivingArrivalAdapter dispatchDrivingArrivalAdapter;

    // 배차 요청서 목록 (승인 / 운행 완료 항목만 노출)
    @GetMapping
    public ResponseEntity<List<DispatchListResponse>> list(@AuthenticationPrincipal AccountPrincipal principal) {
        return ResponseEntity
                .ok(viewDispatchListUseCase.handle(ViewDispatchListUseCase.ViewDispatchListCommand.ofDriver(UUID.fromString(principal.actor().id()))));
    }

    // 배차 요청서 정보 조회
    @GetMapping("{dispatchId}")
    public ResponseEntity<DispatchInfoResponse> info(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity
                .ok(viewDispatchInfoAdapter.handle(ViewDispatchInfoCommand.ofDriver(new DispatchId(dispatchId), UUID.fromString(principal.actor().id()))));
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
