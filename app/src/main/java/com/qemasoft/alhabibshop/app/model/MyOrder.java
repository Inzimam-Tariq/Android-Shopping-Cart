package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyOrder {
    private String orderId;
    private String orderStatus;
    private String orderQty;
    private String orderTotalPrice;
    private String orderDate;


    public MyOrder(String orderId, String orderQty, String orderTotalPrice) {
        this.orderId = orderId;
        this.orderQty = orderQty;
        this.orderTotalPrice = orderTotalPrice;
    }

    public MyOrder(String orderId, String orderStatus, String orderQty,
                   String orderTotalPrice) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderQty = orderQty;
        this.orderTotalPrice = orderTotalPrice;
    }
    public MyOrder(String orderId, String orderStatus, String orderQty,
                   String orderTotalPrice, String orderDate) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderQty = orderQty;
        this.orderTotalPrice = orderTotalPrice;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

}
