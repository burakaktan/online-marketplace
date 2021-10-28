package com.obss.jss.finalproject.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;
import com.obss.jss.finalproject.model.User;

public interface UserService
{
	User createNewUser(User user);
	User findUserById(Long id);
	List<User> findAllUsers();
	Boolean existsByUsername(String username);
	User findByUsername(String username);
	User addToFavouritesList(Product p, String username);
	List<Product> getFavouritesList(String username);
	List<String> getFavouritesListNames(String username);
	User removeFromFavouritesList(Product product, String username);
	Page<User> findByNameLike(Integer no, Integer size, String username);
	boolean deleteUser(String username);
	User addToBlackList(String sellerName, String username);
	List<Seller> getBlackList(String username);
	List<String> getBlackListNames(String username);
	User removeFromBlackList(Seller seller, String username);
	boolean editUser(String newName, String oldName);
}
