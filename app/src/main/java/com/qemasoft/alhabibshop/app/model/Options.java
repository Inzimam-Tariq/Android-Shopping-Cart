package com.qemasoft.alhabibshop.app.model;

import java.util.List;

public class Options {

    private String productOptionId;
    private List<ProductOptionValueItem> productOptionValueItemList;
    private String name;
    private String optionId;
    private String type;
    private String value;
    private String required;

    public Options(String value) {
        this.value = value;
    }

    public Options(String productOptionId, List<ProductOptionValueItem> productOptionValueItemList,
                   String name, String optionId) {
        this.productOptionId = productOptionId;
        this.productOptionValueItemList = productOptionValueItemList;
        this.name = name;
        this.optionId = optionId;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public List<ProductOptionValueItem> getProductOptionValueItemList() {
        return productOptionValueItemList;
    }

    public String getName() {
        return name;
    }

    public String getOptionId() {
        return optionId;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getRequired() {
        return required;
    }
}