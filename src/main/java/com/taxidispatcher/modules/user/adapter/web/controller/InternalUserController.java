package com.taxidispatcher.modules.user.adapter.web.controller;

import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserAccountResponse;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountCommand;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("internal/users")
@RestController
@RequiredArgsConstructor
public class InternalUserController {
    private final InternalSearchUserAccountUseCase internalSearchUserAccountUseCase;

    @GetMapping("account/{id}")
    public ResponseEntity<InternalUserAccountResponse> accountId(@PathVariable(name = "id") String accountId) {
        return ResponseEntity
                .ok(internalSearchUserAccountUseCase.handle(new InternalSearchUserAccountCommand(UUID.fromString(accountId))));
    }
}
