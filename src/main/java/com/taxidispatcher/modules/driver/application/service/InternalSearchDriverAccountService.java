package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverAccountResponse;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverAccountCommand;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverAccountUseCase;
import com.taxidispatcher.modules.driver.application.port.out.DriverRepository;
import com.taxidispatcher.modules.driver.domain.aggregate.Driver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalSearchDriverAccountService implements InternalSearchDriverAccountUseCase {
    private final DriverRepository driverRepository;

    @Override
    public InternalDriverAccountResponse handle(InternalSearchDriverAccountCommand command) {
        Driver driver = driverRepository.findByAccountId(command.accountId())
                .orElseThrow(() -> new IllegalArgumentException("기사 정보를 조회할 수 없습니다."));
        return new InternalDriverAccountResponse(driver.getId().id());
    }
}
