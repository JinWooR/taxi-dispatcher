package com.taxidispatcher.modules.account.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordLoginRequest(
        @NotBlank
        String loginId,
        @NotBlank
        String password
) {
}
