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
@RequestMapping("products")
public class ProductController {
	// <----- Attributes ----->
	// Dependency injection
	private AppService appService;

	// <----- Constructors ----->
	public ProductController(AppService appService) {
		this.appService = appService;
	}
	
	// <---- Methods ----->
	// GET
	// GET route for new
	@RequestMapping(value="new", method = RequestMethod.GET)
	public String newProduct(@ModelAttribute("product") Product product) {
		return "/products/new.jsp";
	}
	
	// GET route for product (show)
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public String showProduct(@PathVariable("id") Long id, Model model) {
		// Model variables
		Product product = appService.findProduct(id);
		List<Category> categories = appService.allCategories();
		List<Category> remCategories = new ArrayList<Category>();
		
		// removes all categories linked to product
		for (Category category : categories) {
			for (Product prodLink : category.getProducts()) {
				if (prodLink.getId().equals(id)) {
					remCategories.add(category);
				}
			}
		}
		
		// Removes found objects
		categories.removeAll(remCategories);
		
		// Binds data to models
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);

		return "/products/show.jsp";
	}
	
	// POST routes
	// POST route for new
	@RequestMapping(value="new", method = RequestMethod.POST)
	public String addNewProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		// event handler for validation errors
		if (result.hasErrors()) {
			return "/products/new.jsp";
		} else { 
			appService.saveProduct(product);
			return "redirect:/products/" + product.getId(); 
		}
	}
	
	// PUT route for product (update)
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	public String updateProduct(@PathVariable("id") Long prodId, @RequestParam("id") Long catId) {
		// data variables
		Product product = appService.findProduct(prodId);
		Category category = appService.findCategory(catId);
		
		// adds data to product
		product.getCategories().add(category);
		appService.saveProduct(product);
		
		return "redirect:/products/" + product.getId(); 		
	}
}
