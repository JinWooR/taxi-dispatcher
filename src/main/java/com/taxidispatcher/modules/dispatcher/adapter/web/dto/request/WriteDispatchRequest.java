package com.taxidispatcher.modules.dispatcher.adapter.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

public record WriteDispatchRequest(
        @NotNull @Validated AddressGeo startAddress,
        @NotNull @Validated AddressGeo arrivalAddress
) {

    @Getter
    public static class AddressGeo {
        @NotBlank String address;
        @NotNull Double x;
        @NotNull Double y;

        public AddressGeo(String address, Double x, Double y) {
            this.address = address;
            this.x = x;
            this.y = y;
        }
    }
}
