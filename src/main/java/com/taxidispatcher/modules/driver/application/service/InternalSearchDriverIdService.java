package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverResponse;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverIdUseCase;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverIdCommand;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.shared.core.AppException;
import com.taxidispatcher.shared.core.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalSearchDriverIdService implements InternalSearchDriverIdUseCase {
    private final DriverRepository driverRepository;

    @Override
    public InternalDriverResponse handle(InternalSearchDriverIdCommand command) {
        var driver = driverRepository.findById(command.driverId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "기사 정보를 확인할 수 없습니다."));

        var taxi = driver.getTaxi();

        return new InternalDriverResponse(
                command.driverId().id(),
                driver.getName(),
                taxi.taxiNumber(),
                taxi.size().getText(),
                taxi.color().equals(TaxiColor.OTHER)
                        ? taxi.otherColor()
                        : taxi.color().getText()
        );
    }
}
