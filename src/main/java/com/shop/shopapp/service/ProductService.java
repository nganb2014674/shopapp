package com.shop.shopapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.shop.shopapp.model.Product;

import com.shop.shopapp.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;
	
	public List<Product> getAll(){
		return repo.findAll();
	}
	
	public Product getById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Product save(Product p) {
		return repo.save(p);
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public Page<Product> search(String keyword,Pageable pageable){
		return repo.findByNameContainingIgnoreCase(keyword, pageable);
	}
	
	public Page<Product> getAll(Pageable pageable){
		return repo.findAll(pageable);
	}
}
