package com.wipro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.model.Product;
import com.wipro.service.ProductService;

@CrossOrigin(origins = "https://proud-dune-0039f2e00.2.azurestaticapps.net")@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@PostMapping
	public Product createProduct(@RequestBody Product newProduct) {
		return productService.saveProduct(newProduct);
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {
		Product oldProduct = productService.getProductById(id);
		oldProduct.setName(newProduct.getName());
		oldProduct.setDescription(newProduct.getDescription());
		oldProduct.setManufacturer(newProduct.getManufacturer());
		oldProduct.setPrice(newProduct.getPrice());
		oldProduct.setQuantity(newProduct.getQuantity());
		return productService.saveProduct(oldProduct);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
