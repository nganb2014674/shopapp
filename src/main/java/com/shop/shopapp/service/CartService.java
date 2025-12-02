package com.shop.shopapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shop.shopapp.model.CartItem;
import com.shop.shopapp.model.Product;

@Service
public class CartService {
	
	private Map<Long, CartItem> cart =new HashMap<Long, CartItem>();
	
	public void add(Product p) {
		if(cart.containsKey(p.getId())) {
			cart.get(p.getId()).setQuantity(cart.get(p.getId()).getQuantity()+1);
		}else {
			CartItem item=new CartItem();
			item.setProduct(p);
			cart.put(p.getId(), item);
		}
	}
	
	public void remove(Long id) {
		cart.remove(id);
	}
	
	public void decrease(Long id) {
		CartItem item=cart.get(id);
		if(item.getQuantity()<=1) {
			cart.remove(id);
		}else {
			item.setQuantity(item.getQuantity()-1);
		}
	}
	
	public List<CartItem> getAll(){
		return new ArrayList<CartItem>(cart.values());
	}
	
	public double getTotal() {
		return cart.values().stream().mapToDouble(CartItem::getTotail).sum();
	}
	
	public void clear() {
		cart.clear();
	}
}
