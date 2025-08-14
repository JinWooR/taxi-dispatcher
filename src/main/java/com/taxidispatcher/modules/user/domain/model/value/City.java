package com.taxidispatcher.modules.user.domain.model.value;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class City {
    private final String sd; // 시도
    private final String sgg; // 시군구
    private final String emd; // 읍면동

    private City(String sd, String sgg, String emd) {
        this.sd = sd;
        this.sgg = sgg;
        this.emd = emd;
    }

    public static City of(
            @NotNull String sd,
            @NotNull String sgg,
            @NotNull String emd
    ) {
        return new City(sd, sgg, emd);
    }

    public String sd() {
        return sd;
    }

    public String sgg() {
        return sgg;
    }

    public String emd() {
        return emd;
    }
}
