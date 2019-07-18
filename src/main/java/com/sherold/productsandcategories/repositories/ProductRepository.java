package com.sherold.productsandcategories.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sherold.productsandcategories.models.Product;

@Repository // designates repo
public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findAll();
}
