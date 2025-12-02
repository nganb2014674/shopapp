package com.shop.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shopapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
