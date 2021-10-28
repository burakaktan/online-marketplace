package com.obss.jss.finalproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;
import com.obss.jss.finalproject.repository.ProductRepository;
import com.obss.jss.finalproject.repository.SellerRepository;

@Service
public class SellerServiceImpl implements SellerService
{
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	ProductRepository productRepository;

	@Override
	public Seller createNewSeller(String name) {
		Seller s = sellerRepository.findByName(name);
		if(s==null)s = new Seller();
		s.setName(name);
		return sellerRepository.save(s);
	}

	@Override
	public Seller findByName(String name) {
		return sellerRepository.findByName(name);
	}

	@Override
	public Page<Seller> findByNameLike(Integer no, Integer size,String name)  {
		Pageable pageable = PageRequest.of(no, size);
		return sellerRepository.findByNameLike(name,pageable);
	}

	@Override
	public boolean deleteSeller(String name) {
		Seller s = sellerRepository.findByName(name);
		if(s != null)
		{
			for(Product p : s.getProducts())
			{
				productRepository.delete(p);
			}
			sellerRepository.delete(s);
		}
		return true;
	}

	@Override
	public boolean editSeller(String newName, String oldName)
	{
		Seller old = sellerRepository.findByName(oldName);
		if(sellerRepository.findByName(newName) != null)return false;
		old.setName(newName);
		sellerRepository.save(old);
		return true;
	}
}
