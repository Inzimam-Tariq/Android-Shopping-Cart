package com.qemasoft.alhabibshop.app.model;

import java.util.List;

public class Product{
	private String total;
	private String orderProductId;
	private String quantity;
	private String price;
	private String productId;
	private String name;
	private String tax;
	private String model;
	private String orderId;
	private List<Object> option;

	public Product(String name, String quantity, String price, String total) {
		this.total = total;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setOrderProductId(String orderProductId){
		this.orderProductId = orderProductId;
	}

	public String getOrderProductId(){
		return orderProductId;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTax(String tax){
		this.tax = tax;
	}

	public String getTax(){
		return tax;
	}

	public void setModel(String model){
		this.model = model;
	}

	public String getModel(){
		return model;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setOption(List<Object> option){
		this.option = option;
	}

	public List<Object> getOption(){
		return option;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"total = '" + total + '\'' + 
			",order_product_id = '" + orderProductId + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + productId + '\'' + 
			",name = '" + name + '\'' + 
			",tax = '" + tax + '\'' + 
			",model = '" + model + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",option = '" + option + '\'' + 
			"}";
		}
}