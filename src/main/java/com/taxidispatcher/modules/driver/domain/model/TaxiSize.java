package com.taxidispatcher.modules.driver.domain.model;

public enum TaxiSize {
    SMALL("소형"), // 소형
    MEDIUM("중형"), // 중형
    LARGE("대형") // 대형
    ;

    private final String text;

    TaxiSize(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
