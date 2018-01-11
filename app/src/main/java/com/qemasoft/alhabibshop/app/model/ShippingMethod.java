package com.qemasoft.alhabibshop.app.model;

public class ShippingMethod {
    private String code;
    private String cost;
    private String taxClassId;
    private String text;
    private String title;

    public ShippingMethod(String code, String cost,
                          String text, String title) {
        this.code = code;
        this.cost = cost;
        this.text = text;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getCost() {
        return cost;
    }

    public String getTaxClassId() {
        return taxClassId;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

}
