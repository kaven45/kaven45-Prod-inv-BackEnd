package com.wipro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wipro.model.Product;
import com.wipro.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found!"));
	}
	
	public Product saveProduct(Product newProduct) {
		return productRepository.save(newProduct);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
