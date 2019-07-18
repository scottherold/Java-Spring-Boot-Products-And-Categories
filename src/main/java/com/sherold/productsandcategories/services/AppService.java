package com.sherold.productsandcategories.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sherold.productsandcategories.models.Category;
import com.sherold.productsandcategories.models.Product;
import com.sherold.productsandcategories.repositories.CategoryRepository;
import com.sherold.productsandcategories.repositories.ProductRepository;

@Service // designats service
public class AppService {
	// <----- Attributes ----->
	// Dependency injection
	private CategoryRepository catRepo;
	private ProductRepository prodRepo;
	
	// <----- Constructors ----->
	public AppService(CategoryRepository catRepo, ProductRepository prodRepo) {
		this.catRepo = catRepo;
		this.prodRepo = prodRepo;
	}
	
	// <----- Methods ----->
	// SELECT * FROM products
	public List<Category> allCategories() {
		return catRepo.findAll();
	}
	
	// SELECT * FROM categories
	public List<Product> allProducts() {
		return prodRepo.findAll();
	}
	
	// SELECT category FROM categories WHERE id =
	public Category findCategory(Long id) {
		// optional for potential null
		Optional<Category> opCat = catRepo.findById(id);
		if(opCat.isPresent()) {
			return opCat.get();
		} else {
			return null;
		}
	}
	
	// SELECT product FROM products WHERE id =
	public Product findProduct(Long id) {
		// optional for potential null
		Optional<Product> opProd = prodRepo.findById(id);
		if(opProd.isPresent()) {
			return opProd.get();
		} else {
			return null;
		}
	}
	
	// INSERT/UPDATE Category
	public Category saveCategory(Category c) {
		return catRepo.save(c);
	}
	
	// INSERT/UPDATE Product
	public Product saveProduct(Product p) {
		return prodRepo.save(p);
	}
}
