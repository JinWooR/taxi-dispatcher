package com.taxidispatcher.modules.dispatcher.adapter.web.controller;

import com.taxidispatcher.modules.dispatcher.adapter.web.dto.request.WriteDispatchRequest;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchInfoResponse;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.DispatchListResponse;
import com.taxidispatcher.modules.dispatcher.adapter.web.dto.response.WriteDispatchResponse;
import com.taxidispatcher.modules.dispatcher.application.port.in.*;
import com.taxidispatcher.modules.dispatcher.domain.model.AddressGeoInfo;
import com.taxidispatcher.modules.dispatcher.domain.model.DispatchId;
import com.taxidispatcher.shared.security.AccountPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("users/me/dispatches")
@RestController
@RequiredArgsConstructor
@Secured("hasRole('USER')")
public class UserDispatcherController {
    private final ViewDispatchListUseCase viewDispatchListUseCase;
    private final ViewDispatchInfoAdapter viewDispatchInfoAdapter;
    private final WriteDispatchAdapter writeDispatchAdapter;
    private final CancelDispatchAdapter cancelDispatchAdapter;

    // 배차 요청서 목록 조회
    @GetMapping
    public ResponseEntity<List<DispatchListResponse>> list(@AuthenticationPrincipal AccountPrincipal principal) {
        return ResponseEntity
                .ok(viewDispatchListUseCase.handle(new ViewDispatchListUseCase.ViewDispatchListCommand(UUID.fromString(principal.actor().id()))));
    }

    // 배차 요청서 정보 조회
    @GetMapping("{dispatchId}")
    public ResponseEntity<DispatchInfoResponse> info(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        return ResponseEntity
                .ok(viewDispatchInfoAdapter.handle(new ViewDispatchInfoCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id()))));
    }

    // 배차 요청
    @PostMapping("write")
    public ResponseEntity<WriteDispatchResponse> writeDispatch(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody WriteDispatchRequest request
    ) {
        var startAddr = request.startAddress();
        var arrivalAddr = request.arrivalAddress();

        var dispatch = writeDispatchAdapter.handle(new WriteDispatchCommand(UUID.fromString(principal.actor().id()),
                new AddressGeoInfo(startAddr.getAddress(), startAddr.getX(), startAddr.getY()),
                new AddressGeoInfo(arrivalAddr.getAddress(), arrivalAddr.getX(), arrivalAddr.getY())));

        var dispatchId = dispatch.getId().id();


        return ResponseEntity
                .created(URI.create("/users/me/dispatches/" + dispatchId.toString()))
                .body(new WriteDispatchResponse(dispatchId));
    }

    // 배차 취소
    @PatchMapping("{dispatchId}/cancel")
    public ResponseEntity<String> cancel(
            @AuthenticationPrincipal AccountPrincipal principal,
            @PathVariable UUID dispatchId
    ) {
        cancelDispatchAdapter.handle(new CancelDispatchCommand(new DispatchId(dispatchId), UUID.fromString(principal.actor().id())));

        return ResponseEntity
                .ok("배차 취소 완료");
    }
}
