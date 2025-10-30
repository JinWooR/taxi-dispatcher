package com.taxidispatcher.modules.user.adapter.web.controller;

import com.taxidispatcher.modules.user.adapter.web.dto.response.InternalUserAccountResponse;
import com.taxidispatcher.shared.core.domain.user.dto.InternalUserResponse;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountCommand;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserAccountUseCase;
import com.taxidispatcher.modules.user.application.port.in.InternalSearchUserIdUseCase;
import com.taxidispatcher.modules.user.domain.model.UserId;
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
    private final InternalSearchUserIdUseCase internalSearchUserIdUseCase;

    @GetMapping("account/{id}")
    public ResponseEntity<InternalUserAccountResponse> accountId(@PathVariable(name = "id") String accountId) {
        return ResponseEntity
                .ok(internalSearchUserAccountUseCase.handle(new InternalSearchUserAccountCommand(UUID.fromString(accountId))));
    }

    @GetMapping("{userId}")
    public ResponseEntity<InternalUserResponse> userInfo(@PathVariable String userId) {
        return ResponseEntity
                .ok(internalSearchUserIdUseCase.handle(new InternalSearchUserIdUseCase.InternalSearchUserIdCommand(UserId.of(userId))));
    }
}
