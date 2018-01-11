package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyItem {
    private String itemId;
    private String itemTitle;
    private String itemPriceFull;
    private String itemPriceSpecial;
    private String itemImage;


    public MyItem(String itemId, String itemTitle, String itemPriceSpecial,
                  String itemPriceFull, String itemImage) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPriceFull = itemPriceFull;
        this.itemPriceSpecial = itemPriceSpecial;
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemPriceSpecial() {
        return itemPriceSpecial;
    }

    public String getItemPriceFull() {
        return itemPriceFull;
    }

    public String getItemImage() {
        return itemImage;
    }

}
