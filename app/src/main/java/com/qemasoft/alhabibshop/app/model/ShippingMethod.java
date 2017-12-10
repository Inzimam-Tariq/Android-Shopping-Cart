package com.qemasoft.alhabibshop.app.model;

public class ShippingMethod {
    private String code;
    private String cost;
    private String taxClassId;
    private String text;
    private String title;
    private String terms;

    public ShippingMethod(String code, String cost,
                          String text, String title) {
        this.code = code;
        this.cost = cost;
        this.text = text;
        this.title = title;
    }

    public ShippingMethod(String code, String title, String terms) {
        this.code = code;
        this.title = title;
        this.terms = terms;
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

    public String getTerms() {
        return terms;
    }
}
