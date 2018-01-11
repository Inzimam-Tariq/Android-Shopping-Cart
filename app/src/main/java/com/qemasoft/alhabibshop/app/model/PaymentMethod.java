package com.qemasoft.alhabibshop.app.model;

public class PaymentMethod {
    private String code;
    private String title;
    private String terms;

    public PaymentMethod(String code, String title, String terms) {
        this.code = code;
        this.title = title;
        this.terms = terms;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getTerms() {
        return terms;
    }
}
