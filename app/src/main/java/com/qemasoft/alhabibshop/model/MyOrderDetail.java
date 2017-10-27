package com.qemasoft.alhabibshop.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyOrderDetail {
    private String orderId;
    private String orderDate;
    private String paymentMethod;
    private String deliveryMethod;
    private String orderStatus;
    private String productName;
    private String productModel;
    private String orderQty;
    private String productPrice;
    private String total;
    private String subTotal;
    private String shippingCost;
    private String grandTotal;

    public MyOrderDetail(String productName,String orderQty, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.orderQty = orderQty;
    }

    public String getOrderId() {
        return orderId;
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

    public String getTotal() {
        return total;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getShippingCost() {
        return shippingCost;
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