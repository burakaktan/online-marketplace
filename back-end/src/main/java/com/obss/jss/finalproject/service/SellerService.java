package com.obss.jss.finalproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.obss.jss.finalproject.model.Seller;

public interface SellerService
{
	Seller createNewSeller(String name);
	Seller findByName(String name);
	Page<Seller> findByNameLike(Integer no, Integer size, String name);
	boolean deleteSeller(String name);
	boolean editSeller(String newName, String oldName);
}
