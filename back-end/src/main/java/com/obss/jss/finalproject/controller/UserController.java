package com.obss.jss.finalproject.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.obss.jss.finalproject.dto.MessageResponse;
import com.obss.jss.finalproject.dto.ProductDto;
import com.obss.jss.finalproject.model.Product;
import com.obss.jss.finalproject.model.Seller;
import com.obss.jss.finalproject.model.User;
import com.obss.jss.finalproject.security.JwtUtils;
import com.obss.jss.finalproject.service.ProductService;
import com.obss.jss.finalproject.service.SellerService;
import com.obss.jss.finalproject.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
  private UserService userService;
	
	@Autowired
	  private ProductService productService;
	
	@Autowired
	  private SellerService sellerService;
	
	@Autowired
	  private JwtUtils jwtUtils;

  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User searchUser(@RequestParam String username) {
    return userService.findByUsername(username);
  }
  
  @GetMapping("/searchUsers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Page<User> searchUsers(@RequestParam Integer no, @RequestParam Integer size,@RequestParam String username) {
	  username = "%" + username + "%";
	  return userService.findByNameLike(no,size,username);
  }
  @DeleteMapping("/deleteUser")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public boolean deleteUser(@RequestParam String username)
  {
    return userService.deleteUser(username);
  }
  @PostMapping("/editUser")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> editUser(@RequestParam String oldName,@RequestParam String newName)
  {
    if(userService.editUser(newName,oldName))return ResponseEntity.ok(new MessageResponse("Successfull"));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("unsuccesfull"));
  }
  
  @PostMapping("/addProduct")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> addProduct(@RequestBody ProductDto p)
  {
    String message = productService.createNewProduct(p);
    return ResponseEntity.ok(new MessageResponse(message));
  }
  
  @DeleteMapping("/deleteProduct")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public boolean deleteProduct(@RequestParam String productName)
  {
    productService.deleteProduct(productName);
	return true;
  }
  
  @GetMapping("/searchSellers")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Page<Seller> searchSellers(@RequestParam Integer no, @RequestParam Integer size,@RequestParam String name) {
	  name = "%" + name + "%";
	  return sellerService.findByNameLike(no,size,name);
  }
  
  @PostMapping("/addSeller")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Seller addSeller(@RequestParam String name)
  {
    return sellerService.createNewSeller(name);
  }
  
  @DeleteMapping("/deleteSeller")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public boolean deleteSeller(@RequestParam String name)
  {
    sellerService.deleteSeller(name);
	return true;
  }
  @PostMapping("/editSeller")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> editSeller(@RequestParam String oldName,@RequestParam String newName)
  {
    if(sellerService.editSeller(newName,oldName)) return ResponseEntity.ok(new MessageResponse("successfull"));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("unsuccessful"));
  }
  
  @PostMapping("/editProduct")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> editProduct(@RequestParam String oldName,@RequestBody ProductDto p)
  {
    if(productService.editProduct(p,oldName)) return ResponseEntity.ok(new MessageResponse("successfull"));
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("unsuccessful"));
  }
  @GetMapping("/searchProduct")
  @PreAuthorize("hasRole('ROLE_USER')")
  public Page<Product> searchProduct(@RequestParam Integer no, @RequestParam Integer size, @RequestParam String name)
  {
	  // special kind of like search
	name = "%" + name + "%";
    return productService.findByNameLike(no,size,name);
  }
  
  @GetMapping("/searchProductAdmin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Page<Product> searchProductAdmin(@RequestParam Integer no, @RequestParam Integer size, @RequestParam String name)
  {
	  // special kind of like search
	name = "%" + name + "%";
    return productService.findByNameLike(no,size,name);
  }
  
  @PostMapping("/addToFavouritesList")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User addToFavourites(@RequestParam String productName,@RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	  Product product = productService.findByName(productName);
	  return userService.addToFavouritesList(product, username);
  }
  
  @DeleteMapping("/removeFromFavouritesList")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User removeFromFavourites(@RequestParam String productName, @RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	  Product product = productService.findByName(productName);
	  return userService.removeFromFavouritesList(product, username);
  }
  
  @GetMapping("/getFavourites")
  @PreAuthorize("hasRole('ROLE_USER')")
  public List<String> getFavourites( @RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	 return userService.getFavouritesListNames(username);
  }
  
  @PostMapping("/addToBlackList")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User addToBlacks(@RequestParam String sellerName,@RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	  return userService.addToBlackList(sellerName, username);
  }
  
  @DeleteMapping("/removeFromBlackList")
  @PreAuthorize("hasRole('ROLE_USER')")
  public User removeFromBlacks(@RequestParam String sellerName, @RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	  Seller product = sellerService.findByName(sellerName);
	  return userService.removeFromBlackList(product, username);
  }
  
  @GetMapping("/getBlacks")
  @PreAuthorize("hasRole('ROLE_USER')")
  public List<String> getBlacks( @RequestHeader("Authorization") String Authorization)
  {
	  String username = jwtUtils.getUserNameFromJwtToken(Authorization.substring(7));
	 return userService.getBlackListNames(username);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
}
