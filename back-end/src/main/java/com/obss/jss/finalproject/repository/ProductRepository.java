package com.obss.jss.finalproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obss.jss.finalproject.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>
{
	Product findByName(String name);

	Page<Product> findByNameLike(String name,Pageable pageable);
}
