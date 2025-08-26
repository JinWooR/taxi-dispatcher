package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.RegisterDriverCommand;
import com.taxidispatcher.modules.driver.application.port.in.RegisterDriverUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterDriverService implements RegisterDriverUseCase {
    private final DriverRepository driverRepository;

    @Override
    public Driver handle(RegisterDriverCommand command) {
        return null;
    }
}
