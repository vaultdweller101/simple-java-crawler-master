package com.codegym.models;

public class Price {
    private Float price;
    private TypePrice typePrice;
    private Unit unit;

    public Price() {
    }

    public Price(Float price, TypePrice typePrice, Unit unit) {
        this.price = price;
        this.typePrice = typePrice;
        this.unit = unit;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public TypePrice getTypePrice() {
        return typePrice;
    }

    public void setTypePrice(TypePrice typePrice) {
        this.typePrice = typePrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
