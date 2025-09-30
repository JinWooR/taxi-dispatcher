package com.taxidispatcher.modules.driver.adapter.web.controller;

import com.taxidispatcher.modules.driver.adapter.web.dto.request.InternalDriverNearbyGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.request.UpdateDriverActiveStatusRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.DriverResponse;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverAccountResponse;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverResponse;
import com.taxidispatcher.modules.driver.application.port.in.*;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import com.taxidispatcher.modules.driver.domain.model.DriverGeo;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
import com.taxidispatcher.modules.driver.domain.model.Taxi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("internal/drivers")
@RestController
@RequiredArgsConstructor
public class InternalDriverController {
    private final InternalSearchDriverAccountUseCase internalSearchDriverAccountUseCase;
    private final InternalSearchDriverIdUseCase internalSearchDriverIdUseCase;
    private final InternalSearchDriverNearbyGeoAdapter internalSearchDriverNearbyGeoAdapter;
    private final UpdateDriverActiveStatusUseCase updateDriverActiveStatusUseCase;

    @GetMapping("account/{id}")
    public ResponseEntity<InternalDriverAccountResponse> accountId(@PathVariable(name = "id") String accountId) {
        return ResponseEntity
                .ok(internalSearchDriverAccountUseCase.handle(new InternalSearchDriverAccountCommand(UUID.fromString(accountId))));
    }

    @GetMapping("{driverId}")
    public ResponseEntity<InternalDriverResponse> driverInfo(@PathVariable String driverId) {
        return ResponseEntity
                .ok(internalSearchDriverIdUseCase.handle(new InternalSearchDriverIdCommand(new DriverId(UUID.fromString(driverId)))));
    }

    @PostMapping("nearby-geo")
    public ResponseEntity<List<UUID>> nearbyGeo(
            @Valid @RequestBody InternalDriverNearbyGeoRequest request
    ) {
        return ResponseEntity
                .ok(internalSearchDriverNearbyGeoAdapter.handle(new InternalSearchDriverNearbyGeoCommand(request.driverIds().stream().map(DriverId::new).toList(), request.x(), request.y(), request.distance())));
    }

    // 택시 상태 변경
    @PostMapping("{driverId}/active-status")
    public ResponseEntity<DriverResponse> changeActiveStatus(
            @PathVariable String driverId,
            @Valid @RequestBody UpdateDriverActiveStatusRequest request
    ) {
        Driver driver = updateDriverActiveStatusUseCase.handle(new UpdateDriverActiveStatusCommand(DriverId.strId(driverId), request.activeStatus()));

        return ResponseEntity
                .ok(toRes(driver));
    }

    private DriverResponse toRes(Driver driver) {
        DriverId driverId = driver.getId();
        DriverGeo curGeo = driver.getCurGeo();
        Taxi taxi = driver.getTaxi();
        return new DriverResponse(driverId.id(), driver.getStatus(), driver.getName(), curGeo.lat(), curGeo.lng(), taxi.taxiNumber(), taxi.size(), taxi.color(), taxi.otherColor(), driver.getActiveStatus());
    }
}
