package com.shop.shopapp.model;

import lombok.Data;

@Data
public class CartItem {
	private Product product;
	private int quantity;
	
	public double getTotail() {
		return product.getPrice()*quantity;
	}
	
}
