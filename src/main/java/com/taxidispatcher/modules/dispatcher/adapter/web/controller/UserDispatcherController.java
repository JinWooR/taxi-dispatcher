package com.taxidispatcher.modules.dispatcher.adapter.web.controller;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.dispatcher.application.port.in.WriteDispatchAdapter;
import com.taxidispatcher.modules.dispatcher.application.port.in.WriteDispatchCommand;
import com.taxidispatcher.modules.dispatcher.domain.model.AddressGeoInfo;
import com.taxidispatcher.shared.security.AccountPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("users/me/dispatches")
@RestController
@RequiredArgsConstructor
@Secured("hasRole('USER')")
public class UserDispatcherController {
    private final WriteDispatchAdapter writeDispatchAdapter;

    // 배차 요청서 정보 조회
    @GetMapping("{dispatchId}")
    public ResponseEntity<String> info(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity.ok(null);
    }

    // 배차 요청
    @PostMapping("write")
    public ResponseEntity<String> writeDispatch(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody WriteDispatchRequest request
    ) {
        var startAddr = request.startAddress();
        var arrivalAddr = request.arrivalAddress();

        writeDispatchAdapter.handle(new WriteDispatchCommand(UUID.fromString(principal.actor().id()),
                new AddressGeoInfo(startAddr.getAddress(), startAddr.getX(), startAddr.getY()),
                new AddressGeoInfo(arrivalAddr.getAddress(), arrivalAddr.getX(), arrivalAddr.getY())));

        return ResponseEntity
                .created(null)
                .body(null);
    }

    // 배차 취소
    @PatchMapping("{dispatchId}/cancel")
    public ResponseEntity<String> cancel(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity.ok(null);
    }
}
