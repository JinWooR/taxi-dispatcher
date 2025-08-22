package com.taxidispatcher.modules.driver.domain.model;

public record Taxi(
        String taxiNumber,
        TaxiSize size,
        TaxiColor color,
        String otherColor
) {
    public Taxi(String taxiNumber, TaxiSize size, TaxiColor color, String otherColor) {
        this.taxiNumber = taxiNumber;
        this.size = size;
        this.color = color;

        if (color == TaxiColor.OTHER) {
            if (otherColor == null || otherColor.isBlank()) {
                throw new IllegalArgumentException("택시 색상이 누락되었습니다.");
            }
            this.otherColor = otherColor;
        } else {
            this.otherColor = "";
        }
    }
}
