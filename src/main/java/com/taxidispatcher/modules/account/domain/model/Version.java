package com.taxidispatcher.modules.account.domain.model;

import lombok.Value;

@Value
public class Version {
    long value;

    private Version(long value) {
        this.value = value;
    }

    public Version of(long value) {
        return new Version(value);
    }
}
