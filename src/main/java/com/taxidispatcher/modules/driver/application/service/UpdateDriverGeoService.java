package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverGeoCommand;
import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverGeoUseCase;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDriverGeoService implements UpdateDriverGeoUseCase {
    @Override
    public Driver handle(UpdateDriverGeoCommand command) {
        return null;
    }
}
