package com.taxidispatcher.modules.driver.adapter.web.dto.request;

import com.taxidispatcher.modules.driver.domain.model.DriverActiveStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateDriverActiveStatusRequest(
        @NotNull DriverActiveStatus activeStatus
) {
}
