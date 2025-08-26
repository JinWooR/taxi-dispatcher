package com.taxidispatcher.modules.driver.adapter.web.dto.request;

import com.taxidispatcher.modules.driver.domain.model.TaxiColor;
import com.taxidispatcher.modules.driver.domain.model.TaxiSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDriverRequest(
        @NotBlank String name,
        @NotBlank String taxiNumber,
        @NotNull TaxiSize taxiSize,
        @NotNull TaxiColor taxiColor,
        String taxiOtherColor
) {
}
