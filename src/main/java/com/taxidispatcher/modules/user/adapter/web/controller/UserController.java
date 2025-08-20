package com.taxidispatcher.modules.user.adapter.web.controller;

import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserIdResponse;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserCommand;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserUseCase;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserCommand;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserUseCase;
import com.taxidispatcher.modules.user.domain.model.UserId;
import com.taxidispatcher.shared.security.AccountPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PostMapping("register")
    public ResponseEntity<UserIdResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        UserId userId = registerUserUseCase.handle(new RegisterUserCommand(null, request.name(), request.address(), request.addressDetail()));

        return ResponseEntity
                .created(URI.create("/users/" + userId.value().toString()))
                .body(new UserIdResponse(userId.value().toString()));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal AccountPrincipal principal) {
        deleteUserUseCase.handle(new DeleteUserCommand(UserId.of(principal.actor().id())));

        return ResponseEntity
                .ok("회원 탈퇴 완료");
    }
}
