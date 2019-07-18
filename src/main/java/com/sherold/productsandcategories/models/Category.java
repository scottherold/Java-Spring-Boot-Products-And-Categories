package com.sherold.productsandcategories.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity // Designates DB entity
@Table(name="categories") // maps table in DB
public class Category {
	// <----- Attributes ----->
	@Id // designates id field
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
	private Long id;
	@Size(min=2, max=20, message="Name must be between 2 and 20 characters!") // Sets character range
	private String name;
	@Column(updatable = false) // column data is immutable after instantiation
	private Date createdAt;
	private Date updatedAt;
	
	// <----- Relationship ----->
	@ManyToMany(fetch = FetchType.LAZY) // establishes relationship type
	 // Sets up middle table in n:m relationship
	@JoinTable(
			name = "categories_products", // table name
			joinColumns = @JoinColumn(name = "category_id"), // where object is mapped on middle-table 
			inverseJoinColumns = @JoinColumn(name = "product_id") // where middle-table maps to other object
	)
	private List<Product> products;

	// <----- Constructors ----->
	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, List<Product> products) {
		this.name = name;
		this.products = products;
	}

	// <----- Getters/Setters ----->
	// name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// products
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	// Getters only
	// id
	public Long getId() {
		return id;
	}

	// createdAt
	public Date getCreatedAt() {
		return createdAt;
	}

	// updatedAt
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	// <----- Methods ----->
	@PrePersist // run at time of instantiation
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate // run at time of update
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
