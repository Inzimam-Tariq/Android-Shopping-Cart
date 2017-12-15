package com.qemasoft.alhabibshop.app.model;

public class ProductOptionValueItem {

    private String optionValueId;
    private boolean price;
    private String productOptionValueId;
    private String name;
    private String pricePrefix;

    public ProductOptionValueItem(String optionValueId, String name) {
        this.optionValueId = optionValueId;
        this.name = name;
    }

    public String getOptionValueId() {
        return optionValueId;
    }

    public boolean isPrice() {
        return price;
    }

    public String getProductOptionValueId() {
        return productOptionValueId;
    }

    public String getName() {
        return name;
    }

    public String getPricePrefix() {
        return pricePrefix;
    }
}
