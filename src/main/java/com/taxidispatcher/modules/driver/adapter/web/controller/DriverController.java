package com.taxidispatcher.modules.driver.adapter.web.controller;

import com.taxidispatcher.modules.driver.adapter.web.dto.request.RegisterDriverRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverTaxiRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.DriverResponse;
import com.taxidispatcher.modules.driver.application.port.in.*;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.Taxi;
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
        Taxi taxi = new Taxi(request.taxiNumber(), request.taxiSize(), request.taxiColor(), request.taxiOtherColor());

        Driver driver = registerDriverUseCase.handle(new RegisterDriverCommand(principal.accountId(), request.name(), taxi));

        DriverResponse res = toRes(driver);

        return ResponseEntity
                .created(URI.create("/drivers/" + res.driverId()))
                .body(res);
    }

    // 택시 정보 변경
    @PreAuthorize("hasRole('DRIVER')")
    @PatchMapping("taxi")
    public ResponseEntity<DriverResponse> changeTaxi(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverTaxiRequest request
    ) {
        DriverId driverId = DriverId.strId(principal.actor().id());
        Taxi taxi = new Taxi(request.taxiNumber(), request.taxiSize(), request.taxiColor(), request.taxiOtherColor());

        Driver driver = updateDriverTaxiUseCase.handle(new UpdateDriverTaxiCommand(driverId, taxi));

        return ResponseEntity
                .ok(toRes(driver));
    }

    // 택시 상태 변경
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("active-status")
    public ResponseEntity<DriverResponse> changeActiveStatus(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverActiveStatusRequest request
    ) {
        DriverId driverId = DriverId.strId(principal.actor().id());

        Driver driver = updateDriverActiveStatusUseCase.handle(new UpdateDriverActiveStatusCommand(driverId, request.activeStatus()));

        return ResponseEntity
                .ok(toRes(driver));
    }

    // 현재 좌표 최신화
    @PreAuthorize("hasRole('DRIVER')")
    @PostMapping("geo")
    public ResponseEntity<DriverResponse> updateGeo(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateDriverGeoRequest request
    ) {
        DriverId driverId = DriverId.strId(principal.actor().id());
        DriverGeo curGeo = new DriverGeo(request.lat(), request.lng());

        Driver driver = updateDriverGeoUseCase.handle(new UpdateDriverGeoCommand(driverId, curGeo, request.deviceTs(), request.seq()));

        return ResponseEntity
                .ok(toRes(driver));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal AccountPrincipal principal
    ) {
        DriverId driverId = DriverId.strId(principal.actor().id());

        deleteDriverUseCase.handle(new DeleteDriverCommand(driverId));

        return ResponseEntity.ok("탈퇴 완료");
    }

    private DriverResponse toRes(Driver driver) {
        DriverId driverId = driver.getId();
        DriverGeo curGeo = driver.getCurGeo();
        Taxi taxi = driver.getTaxi();
        return new DriverResponse(driverId.id(), driver.getStatus(), driver.getName(), curGeo.lat(), curGeo.lng(), taxi.taxiNumber(), taxi.size(), taxi.color(), taxi.otherColor(), driver.getActiveStatus());
    }
}
