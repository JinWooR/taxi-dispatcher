package com.taxidispatcher.modules.dispatcher.domain.model;

public record DispatchGeoId(
        DispatchId dispatchId,
        Long seq
) {
}
