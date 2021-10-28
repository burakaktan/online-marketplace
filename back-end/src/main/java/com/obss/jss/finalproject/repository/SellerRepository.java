package com.obss.jss.finalproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long>{

	Seller findByName(String name);
//	Page<Seller> findByNameLike(String name,Pageable pageable);
//
//	Seller findByName(String name);

	Page<Seller> findByNameLike(String name, Pageable pageable);
}
