package com.taxidispatcher.modules.user.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserNameRequest(
        @NotBlank String name
) {
}
