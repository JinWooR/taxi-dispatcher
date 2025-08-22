package com.taxidispatcher.modules.driver.domain.model;

public record WorkGeoId(
        WorkHistoryId workHistoryId,
        Long seq
) {
}
