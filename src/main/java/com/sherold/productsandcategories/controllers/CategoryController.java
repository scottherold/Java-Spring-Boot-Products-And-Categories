package com.sherold.productsandcategories.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sherold.productsandcategories.models.Category;
import com.sherold.productsandcategories.models.Product;
import com.sherold.productsandcategories.services.AppService;

@Controller // Designates as a controller
@RequestMapping("categories")
public class CategoryController {
	// <----- Attributes ----->
	// Dependency injection
	private AppService appService;

	// <----- Constructors ----->
	public CategoryController(AppService appService) {
		this.appService = appService;
	}
	
	// <---- Methods ----->
	// GET
	// GET route for new
	@RequestMapping(value="new", method = RequestMethod.GET)
	public String newCategory(@ModelAttribute("category") Category category) {
		return "/categories/new.jsp";
	}
	
	// GET route for categories (show)
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public String showCategory(@PathVariable("id") Long id, Model model) {
		// Model variables
		Category category = appService.findCategory(id);
		List<Product> products = appService.allProducts();
		List<Product> remProducts = new ArrayList<Product>();
		
		// removes all categories linked to product
		for (Product product : products) {
			for (Category catLink : product.getCategories()) {
				if (catLink.getId().equals(id)) {
					remProducts.add(product);
				}
			}
		}
		
		// Removes found objects
		products.removeAll(remProducts);
		
		// Binds data to models
		model.addAttribute("category", category);
		model.addAttribute("products", products);

		return "/categories/show.jsp";
	}
	
	// POST routes
	// POST route for new
	@RequestMapping(value="new", method = RequestMethod.POST)
	public String addNewCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
		// event handler for validation errors
		if (result.hasErrors()) {
			return "/categories/new.jsp";
		} else { 
			appService.saveCategory(category);
			return "redirect:/categories/" + category.getId(); 
		}
	}
	
	// PUT route for category (update)
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	public String updateProduct(@PathVariable("id") Long catId, @RequestParam("id") Long prodId) {
		// data variables
		Category category = appService.findCategory(catId);
		Product product = appService.findProduct(prodId);
		
		// adds data to product
		category.getProducts().add(product);
		appService.saveCategory(category);
		
		return "redirect:/categories/" + category.getId(); 		
	}
}
