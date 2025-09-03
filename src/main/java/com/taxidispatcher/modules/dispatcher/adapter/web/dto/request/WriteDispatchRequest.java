package com.taxidispatcher.modules.dispatcher.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

public record WriteDispatchRequest(
        @NotNull @Validated AddressGeo startAddress,
        @NotNull @Validated AddressGeo arrivalAddress
) {
    public static class AddressGeo {
        @NotBlank String address;
        @NotBlank String x;
        @NotBlank String y;
    }
}
