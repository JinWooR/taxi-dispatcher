package com.taxidispatcher.modules.driver.adapter.web.controller;

import com.taxidispatcher.modules.driver.adapter.web.dto.request.InternalDriverNearbyGeoRequest;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverAccountResponse;
import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverResponse;
import com.taxidispatcher.modules.driver.application.port.in.*;
import com.taxidispatcher.modules.driver.domain.model.DriverId;
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
}
