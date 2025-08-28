package com.taxidispatcher.modules.driver.adapter.web.controller;

import com.taxidispatcher.modules.driver.adapter.web.dto.response.InternalDriverAccountResponse;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverAccountCommand;
import com.taxidispatcher.modules.driver.application.port.in.InternalSearchDriverAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("internal/drivers")
@RestController
@RequiredArgsConstructor
public class InternalDriverController {
    private final InternalSearchDriverAccountUseCase internalSearchDriverAccountUseCase;

    @GetMapping("account/{id}")
    public ResponseEntity<InternalDriverAccountResponse> accountId(@PathVariable(name = "id") String accountId) {
        return ResponseEntity
                .ok(internalSearchDriverAccountUseCase.handle(new InternalSearchDriverAccountCommand(UUID.fromString(accountId))));
    }
}
