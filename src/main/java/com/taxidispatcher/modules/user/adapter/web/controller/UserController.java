package com.taxidispatcher.modules.user.adapter.web.controller;

import com.taxidispatcher.modules.user.adapter.web.dto.request.RegisterUserRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserAddressRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.request.UpdateUserNameRequest;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserIdResponse;
import com.taxidispatcher.modules.user.adapter.web.dto.response.UserResponse;
import com.taxidispatcher.modules.user.application.port.in.*;
import com.taxidispatcher.modules.user.domain.model.UserId;
import com.taxidispatcher.shared.security.AccountPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserNameUseCase updateUserNameUseCase;
    private final UpdateUserAddressUseCase updateUserAddressUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("register")
    public ResponseEntity<UserIdResponse> register(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody RegisterUserRequest request
    ) {
        UserId userId = registerUserUseCase.handle(new RegisterUserCommand(principal.accountId(), request.name(), request.address(), request.addressDetail()));

        return ResponseEntity
                .created(URI.create("/users/" + userId.value().toString()))
                .body(new UserIdResponse(userId.value().toString()));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("name")
    public ResponseEntity<UserResponse> changeName(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateUserNameRequest request
    ) {
        var res = updateUserNameUseCase.handle(new UpdateUserNameCommand(UserId.of(principal.actor().id()), request.name()));

        return ResponseEntity
                .ok(res);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("address")
    public ResponseEntity<UserResponse> changeAddress(
            @AuthenticationPrincipal AccountPrincipal principal,
            @Valid @RequestBody UpdateUserAddressRequest request
    ) {
        var res = updateUserAddressUseCase.handle(new UpdateUserAddressCommand(UserId.of(principal.actor().id()), request.address(), request.addressDetail()));

        return ResponseEntity
                .ok(res);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal AccountPrincipal principal) {
        deleteUserUseCase.handle(new DeleteUserCommand(UserId.of(principal.actor().id())));

        return ResponseEntity
                .ok("회원 탈퇴 완료");
    }
}
