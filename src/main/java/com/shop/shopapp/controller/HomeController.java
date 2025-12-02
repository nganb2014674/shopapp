package com.shop.shopapp.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.shopapp.model.Product;
import com.shop.shopapp.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	@GetMapping("/")
	public String home(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "") String keyword,Model model) {
		Pageable pageable=PageRequest.of(page, 8);
		Page<Product> productPage;
		if(!keyword.isEmpty()) {
			productPage=productService.search(keyword,pageable);
		}else {
			productPage=productService.getAll(pageable);
		}
		model.addAttribute("product", productPage.getContent());
		model.addAttribute("totalPages", productPage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		
		
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		String email=auth.getName();
		model.addAttribute("email",email);
		return "index";
	}

}
