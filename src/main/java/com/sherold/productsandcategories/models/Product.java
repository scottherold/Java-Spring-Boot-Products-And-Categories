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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Entity // Designates DB entity
@Table(name="products") // maps table in DB
public class Product {
	// <----- Attributes ----->
	@Id // designates id field
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
	private Long id;
	@Size(min=2, max=20, message="Name must be between 2 and 20 characters!") // Sets character range
	private String name;
	@Size(min=10, max=100, message="Description must be between 10 and 100 characters!") // Sets character range
	private String description;
	@DecimalMin(value="0.01", message="The minimum price must be above 0.00") // Sets minimum for price
	@Digits(integer=6, fraction=2, message="Please use the correct format!") // sets proper formatting for price
	private Float price;
	@Column(updatable = false) // column data is immutable after instantiation
	private Date createdAt;
	private Date updatedAt;
	
	// <----- Relationship ----->
	@ManyToMany(fetch = FetchType.LAZY) // establishes relationship type
	 // Sets up middle table in n:m relationship
	@JoinTable(
			name = "categories_products", // table name
			joinColumns = @JoinColumn(name = "product_id"), // where object is mapped on middle-table 
			inverseJoinColumns = @JoinColumn(name = "category_id") // where middle-table maps to other object
	)
	private List<Category> categories;

	// <----- Constructors ----->
	public Product() {
	}

	public Product(String name,	String description, Float price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Product(String name, String description, Float price, List<Category> categories) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.categories = categories;
	}

	// <----- Getters/Setters ----->
	// name
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// description
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// price
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	// categories
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
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
	
	// <---- Methods ----->
	@PrePersist // run at time of instantiation
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate // run at time of update
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
