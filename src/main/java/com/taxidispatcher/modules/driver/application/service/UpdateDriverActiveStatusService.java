package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverActiveStatusCommand;
import com.taxidispatcher.modules.driver.application.port.in.UpdateDriverActiveStatusUseCase;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateDriverActiveStatusService implements UpdateDriverActiveStatusUseCase {
    @Override
    public Driver handle(UpdateDriverActiveStatusCommand command) {
        return null;
    }
}
