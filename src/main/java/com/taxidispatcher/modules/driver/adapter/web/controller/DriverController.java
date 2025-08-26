package com.taxidispatcher.modules.driver.adapter.web.controller;

import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverTaxiRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.DriverResponse;
import com.taxidispatcher.modules.driver.application.port.in.*;
import com.taxidispatcher.shared.security.AccountPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("drivers")
@RestController
@RequiredArgsConstructor
public class DriverController {
    private final RegisterDriverUseCase registerDriverUseCase;
    private final UpdateDriverTaxiUseCase updateDriverTaxiUseCase;
    private final UpdateDriverGeoUseCase updateDriverGeoUseCase;
    private final UpdateDriverActiveStatusUseCase updateDriverActiveStatusUseCase;
    private final DeleteDriverUseCase deleteDriverUseCase;


    // 기사 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("register")
    public ResponseEntity<DriverResponse> register(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody RegisterDriverRequest request
    ) {

        return ResponseEntity
                .created(URI.create("/drivers/" + ""))
                .body(null);
    }

    // 택시 정보 변경
    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("taxi")
    public ResponseEntity<DriverResponse> changeTaxi(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverTaxiRequest request
    ) {

        return ResponseEntity
                .ok(null);
    }

    // 택시 상태 변경
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("active-status")
    public ResponseEntity<DriverResponse> changeActiveStatus(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverActiveStatusRequest request
    ) {

        return ResponseEntity
                .ok(null);
    }

    // 현재 좌표 최신화
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("geo")
    public ResponseEntity<DriverResponse> get(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverGeoRequest request
    ) {

        return ResponseEntity
                .ok(null);
    }

    @PreAuthorize("hasRole('DRIVER')")
    @DeleteMapping
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal AccountPrincipal principal
    ) {
        return ResponseEntity.ok("탈퇴 완료");
    }
}
