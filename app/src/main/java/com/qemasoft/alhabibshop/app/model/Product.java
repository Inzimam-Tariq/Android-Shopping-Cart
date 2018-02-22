package com.qemasoft.alhabibshop.app.model;

import java.util.List;

public class Product {
    
    private String productId;
    private String name;
    private String model;
    private String price;
    private String spacialPrice;
    private String quantity;
    private String productDescription;
    private String stockStatus;
    private String manufacturer;
    private String discount;
    private String discPercent;
    private String dateAdded;
    private String reviewCount;
    private String rating;
    private String tax;
    private String orderId;
    private List<Object> option;
    private String total;
    private String orderProductId;
    private List<Reviews> reviewsList;
    private String url;
    
    
    public Product(String name, String quantity, String price, String total) {
        this.total = total;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }
    
    public Product(String productId, String name, String model, String price, String spacialPrice,
                   String quantity, String productDescription, String stockStatus,
                   String manufacturer, String discount, String discPercent,
                   String dateAdded, String rating, String reviewCount, String url) {
        this.productId = productId;
        this.name = name;
        this.model = model;
        this.price = price;
        this.spacialPrice = spacialPrice;
        this.quantity = quantity;
        this.productDescription = productDescription;
        this.stockStatus = stockStatus;
        this.manufacturer = manufacturer;
        this.discPercent = discPercent;
        this.discount = discount;
        this.dateAdded = dateAdded;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.url = url;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getModel() {
        return model;
    }
    
    public String getPrice() {
        return price;
    }
    
    public String getSpacialPrice() {
        return spacialPrice;
    }
    
    public String getQuantity() {
        return quantity;
    }
    
    public String getProductDescription() {
        return productDescription;
    }
    
    public String getStockStatus() {
        return stockStatus;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public String getDiscPercent() {
        return discPercent;
    }
    
    public String getDiscount() {
        return discount;
    }
    
    public String getDateAdded() {
        return dateAdded;
    }
    
    public String getReviewCount() {
        return reviewCount;
    }
    
    public String getRating() {
        return rating;
    }
    
    public String getTax() {
        return tax;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public List<Object> getOption() {
        return option;
    }
    
    public String getTotal() {
        return total;
    }
    
    public String getOrderProductId() {
        return orderProductId;
    }
    
    public List<Reviews> getReviewsList() {
        return reviewsList;
    }
    
    public String getUrl() {
        return url;
    }
}