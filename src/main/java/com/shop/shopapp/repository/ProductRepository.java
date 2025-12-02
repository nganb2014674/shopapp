package com.shop.shopapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.shopapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findByNameContainingIgnoreCase(String keyword,Pageable pageable);
}
