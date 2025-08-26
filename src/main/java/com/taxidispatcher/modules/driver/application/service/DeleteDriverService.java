package com.taxidispatcher.modules.driver.application.service;

import com.taxidispatcher.modules.driver.application.port.in.DeleteDriverCommand;
import com.taxidispatcher.modules.driver.application.port.in.DeleteDriverUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteDriverService implements DeleteDriverUseCase {
    @Override
    public void handle(DeleteDriverCommand command) {

    }
}
