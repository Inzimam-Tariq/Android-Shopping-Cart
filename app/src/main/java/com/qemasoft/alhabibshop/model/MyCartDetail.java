package com.qemasoft.alhabibshop.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyCartDetail {
    private String cartId;
    private String orderDate;
    private String paymentMethod;
    private String deliveryMethod;
    private String orderStatus;
    private String productName;
    private String productModel;
    private String orderQty;
    private String productPrice;
    private String subTotal;
    private String couponDiscount;
    private String grandTotal;

    public MyCartDetail(String productModel, String productName, String orderQty,
                        String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderQty = orderQty;
        this.productModel = productModel;
    }

    public String getCartId() {
        return cartId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getCouponDiscount() {
        return couponDiscount;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}