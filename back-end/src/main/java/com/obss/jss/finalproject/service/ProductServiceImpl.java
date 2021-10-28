package com.obss.jss.finalproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obss.jss.finalproject.dto.ProductDto;
import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;
import com.obss.jss.finalproject.repository.ProductRepository;
import com.obss.jss.finalproject.repository.SellerRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SellerRepository sellerRepository;
	
	@Override
	public String createNewProduct(ProductDto p) {
		Product check = productRepository.findByName(p.getName());
		if(check != null) {return "This product name is already exist!";}
		Product yeni = new Product();
		yeni.setName(p.getName());
		yeni.setPrice(p.getPrice());
		yeni.setStok(p.getStok());
		yeni.setSeller(sellerRepository.findByName(p.getSellerName()));
		Product toReturn =  productRepository.save(yeni);
		return "Product is added";
	}

	@Override
	public Optional<Product> findProductById(Long id) {
		return productRepository.findById(id);
		
	}

	@Override
	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public Page<Product> findByNameLike(Integer no, Integer size, String name) {
		Pageable p = PageRequest.of(no, size);
		return productRepository.findByNameLike(name,p);
	}

	@Override
	public void deleteProduct(String productName)
	{
		Product product = productRepository.findByName(productName);
		product.getSeller().removeFromProducts(product);
		productRepository.delete(product);
	}

	@Override
	public boolean editProduct(ProductDto p, String oldName) {
		if(productRepository.findByName(p.getName()) != null)return false;
		if(productRepository.findByName(oldName) == null)return false;
		Product old = productRepository.findByName(oldName);
		old.setName(p.getName());
		old.setPrice(p.getPrice());
		old.setStok(p.getStok());
		productRepository.save(old);
		return true;
	}

}
