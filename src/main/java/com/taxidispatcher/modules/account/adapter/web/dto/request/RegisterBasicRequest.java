package com.taxidispatcher.modules.account.adapter.web.dto.request;

import com.taxidispatcher.modules.account.domain.model.IdentifierKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterBasicRequest(
    @NotNull
    IdentifierKind kind,
    @NotBlank
    String loginId,
    @NotBlank
    String password
) {}