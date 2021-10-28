package com.obss.jss.finalproject.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User extends BaseEntity
{
	@NotBlank
	@Size(max=150)
	private String username;
	
	@NotBlank
	@Size(max=150)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Product> favouriteList = new ArrayList<Product>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Seller> blackList = new ArrayList<Seller>();

	public User()
	{
		
	}
	
	public User(String username,String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public List<Product> getFavouriteList() {
		return favouriteList;
	}

	public void setFavouriteList(List<Product> favouriteList) {
		this.favouriteList = favouriteList;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Seller> getBlackList() {
		return blackList;
	}

	public void setBlackList(List<Seller> blackList) {
		this.blackList = blackList;
	}
	
	public void addToFavouritesList(Product p)
	{
		List<Product> fl = this.getFavouriteList();
		if(fl == null)
		{
			this.setFavouriteList(new ArrayList<Product>());
			fl = this.getFavouriteList();
		}
		fl.add(p);
	}
	
	public void removeFromFavouritesList(Product p)
	{
		List<Product> fl = this.getFavouriteList();
		if(fl == null)
		{
			this.setFavouriteList(new ArrayList<Product>());
			fl = this.getFavouriteList();
		}
		fl.remove(p);
	}
	
	public void addToBlackList(Seller s)
	{
		List<Seller> pl = this.getBlackList();
		if(pl == null)
		{
			this.setBlackList(new ArrayList<Seller>());
			pl = this.getBlackList();
		}
		pl.add(s);
	}
	
	public void removeFromBlackList(Seller s)
	{
		List<Seller> pl = this.getBlackList();
		if(pl == null)
		{
			this.setBlackList(new ArrayList<Seller>());
			pl = this.getBlackList();
		}
		pl.remove(s);
	}
}
