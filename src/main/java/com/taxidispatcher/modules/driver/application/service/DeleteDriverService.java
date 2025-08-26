package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.DeleteDriverCommand;
import com.taxidispatcher.modules.driver.application.port.in.DeleteDriverUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDriverService implements DeleteDriverUseCase {
    private final DriverRepository driverRepository;

    @Override
    public void handle(DeleteDriverCommand command) {
        Driver driver = driverRepository.findById(command.driverId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 택시 기사 정보입니다."));

        driver.delete();
        driverRepository.delete(command.driverId());
    }
}
