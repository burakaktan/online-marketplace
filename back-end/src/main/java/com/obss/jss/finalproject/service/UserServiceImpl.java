package com.obss.jss.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;
import com.obss.jss.finalproject.model.User;
import com.obss.jss.finalproject.repository.SellerRepository;
import com.obss.jss.finalproject.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	UserRepository userRepository;
	@Autowired
	SellerRepository sellerRepository;
	
	public User createNewUser(User user)
	{
		return userRepository.save(user);
	}
	
	public List<User> findAllUsers()
	{
		return userRepository.findAll();
	}
	
	public User findUserById(Long id)
	{
		return userRepository.getById(id);
	}
	
	@Override
	  public Boolean existsByUsername(String username) {
	    Objects.requireNonNull(username, "username cannot be null");
	    return userRepository.existsByUsername(username);
	  }

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User addToFavouritesList(Product p, String username)
	{
		User u = findByUsername(username);
		 u.addToFavouritesList(p);
		 return userRepository.save(u);
	}

	@Override
	public List<Product> getFavouritesList(String username) {
		User u = findByUsername(username);
		return u.getFavouriteList();
	}

	@Override
	public List<String> getFavouritesListNames(String username) {
		User u = findByUsername(username);
		List<String> l = new ArrayList<String>();
		for(Product p : u.getFavouriteList())
			l.add(p.getName());
		return l;
	}

	@Override
	public User removeFromFavouritesList(Product product, String username) {
		User u = findByUsername(username);
		 u.removeFromFavouritesList(product);
		 return userRepository.save(u);
	}
	
	@Override
	public User addToBlackList(String sellerName, String username)
	{
		User u = findByUsername(username);
		Seller s = sellerRepository.findByName(sellerName);
		 u.addToBlackList(s);
		 return userRepository.save(u);
	}

	@Override
	public List<Seller> getBlackList(String username) {
		User u = findByUsername(username);
		return u.getBlackList();
	}

	@Override
	public List<String> getBlackListNames(String username) {
		User u = findByUsername(username);
		List<String> l = new ArrayList<String>();
		for(Seller s : u.getBlackList())
			l.add(s.getName());
		return l;
	}

	@Override
	public User removeFromBlackList(Seller seller, String username) {
		User u = findByUsername(username);
		 u.removeFromBlackList(seller);
		 return userRepository.save(u);
	}

	@Override
	public Page<User> findByNameLike(Integer no, Integer size, String username) {
		Pageable pageable = PageRequest.of(no, size);
		return userRepository.findByUsernameLike(username,pageable);
	}

	@Override
	public boolean deleteUser(String username) {
		User u = userRepository.findByUsername(username);
		userRepository.delete(u);
		return true;
	}

	@Override
	public boolean editUser(String newName, String oldName) {
		User u = userRepository.findByUsername(oldName);
		if(userRepository.findByUsername(newName) != null)return false;
		u.setUsername(newName);
		userRepository.save(u);
		return true;
	}
}
