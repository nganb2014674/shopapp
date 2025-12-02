package com.shop.shopapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.shopapp.model.Order;
import com.shop.shopapp.model.OrderDetail;
import com.shop.shopapp.model.Product;
import com.shop.shopapp.repository.OrderRepository;
import com.shop.shopapp.service.CartService;
import com.shop.shopapp.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cart;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderRepository oderRepo;
	
	@GetMapping
	public String viewCart(Model model) {
		model.addAttribute("items",cart.getAll());
		model.addAttribute("totail", cart.getTotal());
		return "cart";
	}
	
	@GetMapping("/add/{id}")
	public String add(@PathVariable Long id) {
		Product p=productService.getById(id);
		cart.add(p);
		return "redirect:/cart";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(@PathVariable Long id) {
		cart.remove(id);
		return "redirect:/cart";
	}
	
	@GetMapping("/decrease/{id}")
	public String decrease(@PathVariable Long id) {
		cart.decrease(id);
		return "redirect:/cart";
	}
	
	@PostMapping("/checkout")
	public String checkout(@RequestParam String name,@RequestParam String email,@RequestParam String address,Model model) {
		Order order=new Order();
		order.setCustomerName(name);
		order.setCustomerEmail(email);
		order.setCustomerAddress(address);
		order.setTotalPrice(cart.getTotal());
		
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		
		for(var item : cart.getAll()) {
			OrderDetail d=new OrderDetail();
			d.setOrder(order);
			d.setProduct(item.getProduct());
			d.setQuantity(item.getQuantity());
			d.setPrice(item.getProduct().getPrice());
			list.add(d);
		}
		order.setDetails(list);
		oderRepo.save(order);
		cart.clear();
		model.addAttribute("order", order);
		return "order_success";
	}
}
