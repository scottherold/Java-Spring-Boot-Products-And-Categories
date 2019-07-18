package com.sherold.productsandcategories.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sherold.productsandcategories.models.Category;

@Repository // designates repo
public interface CategoryRepository extends CrudRepository<Category, Long> {
	List<Category> findAll();
}
