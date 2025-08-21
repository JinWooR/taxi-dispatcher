package com.taxidispatcher.modules.user.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserAddressRequest(
        @NotBlank String address,
        @NotBlank String addressDetail
) {
}
