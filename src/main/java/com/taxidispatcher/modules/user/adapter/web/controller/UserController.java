package com.taxidispatcher.modules.user.adapter.web.controller;

import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserIdResponse;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserCommand;
import com.taxidispatcher.modules.user.application.port.in.DeleteUserUseCase;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserCommand;
import com.taxidispatcher.modules.user.application.port.in.RegisterUserUseCase;
import com.taxidispatcher.modules.user.domain.model.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("delete")
    public ResponseEntity<String> delete() {
        deleteUserUseCase.handle(new DeleteUserCommand(null));

        return ResponseEntity
                .ok("회원 탈퇴 완료");
    }
}
