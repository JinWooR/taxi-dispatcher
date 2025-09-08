package com.taxidispatcher.modules.driver.domain.model;

public enum TaxiColor {
    WHITE("흰색"),
    SILVER("은색"),
    BLACK("검정색"),
    OTHER("다른 색상");

    private final String text;

    TaxiColor(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
