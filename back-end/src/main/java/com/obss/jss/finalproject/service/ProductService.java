package com.obss.jss.finalproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.obss.jss.finalproject.dto.ProductDto;
import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;

public interface ProductService
{
	Optional<Product> findProductById(Long id);
	Product findByName(String name);
	Page<Product> findByNameLike(Integer no, Integer size, String name);
	String createNewProduct(ProductDto p);
	void deleteProduct(String productName);
	boolean editProduct(ProductDto p, String oldName);
}
