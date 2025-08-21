package com.taxidispatcher.modules.user.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RegisterUserRequest(
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String addressDetail
) {
}
