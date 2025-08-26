package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverTaxiCommand;
import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverTaxiUseCase;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDriverTaxiService implements UpdateDriverTaxiUseCase {
    @Override
    public Driver handle(UpdateDriverTaxiCommand command) {
        return null;
    }
}
