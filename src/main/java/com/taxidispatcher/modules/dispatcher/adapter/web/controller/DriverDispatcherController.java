package com.taxidispatcher.modules.dispatcher.adapter.web.controller;

import com.taxidispatcher.modules.dispatcher.application.port.in.ApprovalDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.ApprovalDispatchCommand;
import com.taxidispatcher.modules.dispatcher.application.port.in.RefusalDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.RefusalDispatchCommand;
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
    // TODO. 테스트 필요
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
        return ResponseEntity
                .ok(null);
    }

    // 목적지 도착
    @PostMapping("{dispatchId}/arrival")
    public ResponseEntity<String> arrival(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity
                .ok(null);
    }

    // 현재 운행 좌표 최신화 (운행정보 기록)
    @PostMapping("{dispatchId}/geo")
    public ResponseEntity<String> updateGeo(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity
                .ok(null);
    }
}
