package com.taxidispatcher.modules.dispatcher.domain.model;

public record AddressGeoInfo(
        String addressName,
        Double x,
        Double y
) {
}
