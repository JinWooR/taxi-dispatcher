package com.taxidispatcher.modules.account.adapter.web.controller;

import com.taxidispatcher.modules.account.adapter.web.dto.request.RegisterBasicRequest;
import com.taxidispatcher.modules.account.adapter.web.dto.response.AccountIdResponse;
import com.taxidispatcher.modules.account.application.port.in.RegisterAccountBasicCommand;
import com.taxidispatcher.modules.account.application.port.in.RegisterAccountBasicUseCase;
import com.taxidispatcher.modules.account.domain.model.AccountId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterAccountBasicUseCase registerAccountBasicUseCase;

    @PostMapping("register")
    public ResponseEntity<AccountIdResponse> register(@Valid @RequestBody RegisterBasicRequest request) {
        AccountId accountId = registerAccountBasicUseCase.handle(new RegisterAccountBasicCommand(request.kind(), request.loginId(), request.password()));

        return ResponseEntity
                .created(URI.create("/accounts/" + accountId.value().toString()))
                .body(new AccountIdResponse(accountId.value().toString()));
    }
}
