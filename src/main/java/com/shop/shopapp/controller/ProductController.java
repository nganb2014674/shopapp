package com.shop.shopapp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.shopapp.model.Product;
import com.shop.shopapp.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.PackagePrivate;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("products", service.getAll());
		return "product_list";
	}
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("product", new Product());
		return "product_add";
	}
	
	@PostMapping("/add")
	public String addProduct(@ModelAttribute Product product,@RequestParam("imageFile") MultipartFile file) throws IOException {
		//thu muc uploads
		File folder=new File(uploadDir);
		if(!folder.exists()) folder.mkdirs();
		
		//luu file
		if(file!=null && !file.isEmpty()) {
			String fileName=file.getOriginalFilename();
			Path saveFile=Paths.get(uploadDir, fileName);
			file.transferTo(saveFile);
			product.setImage(fileName);
		}
		
		service.save(product);
		return "redirect:/products";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id ) {
		Product product=service.getById(id);
		if(product!=null && product.getImage()!=null) {
			try {
				Path p=Paths.get(uploadDir, product.getImage());
				Files.deleteIfExists(p);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		service.delete(id);
		return "redirect:/products";
	}
	
	@GetMapping("/update/{id}")
	public String editForm(@PathVariable Long id,Model model) {
		Product product=service.getById(id);
		if(product==null) {
			return "redirect:/products";
		}
		model.addAttribute("product", product);
		return "product_edit";
	}
	
	@PostMapping("/update/{id}")
	public String editProduct(@PathVariable Long id,@ModelAttribute Product product,@RequestParam("imageFile") MultipartFile file) throws Exception{
		Product exitsting=service.getById(id);
		if(exitsting==null) return "redirect:/products";
		exitsting.setName(product.getName());
		exitsting.setPrice(product.getPrice());
		exitsting.setDescription(product.getDescription());
		
		File folder=new File(uploadDir);
		if(!folder.exists()) folder.mkdirs();
		
		if(file!=null && !file.isEmpty()) {
			String newFileName=System.currentTimeMillis()+"_"+file.getOriginalFilename();
			Path savePath=Paths.get(uploadDir, newFileName);
			
			//Files.copy(file.getInputStream(), savePath);
			file.transferTo(savePath.toFile());
			
			if(exitsting.getImage()!=null && !exitsting.getImage().isBlank()) {
				Path oldPath=Paths.get(uploadDir, exitsting.getImage());
				try {
					Files.deleteIfExists(oldPath);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			exitsting.setImage(newFileName);
		}
		service.save(exitsting);
		return "redirect:/products";
	}
	
}

