package com.taxidispatcher.modules.user.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Address {
    private final String address;
    private final String addressDetail;

    private Address(String address, String addressDetail) {
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public static Address of(
            @NotNull String address,
            @NotNull String addressDetail
    ) {
        return new Address(address, addressDetail);
    }

    public String address() {
        return address;
    }

    public String addressDetail() {
        return addressDetail;
    }
}
