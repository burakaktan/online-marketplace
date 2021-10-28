package com.obss.jss.finalproject.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Seller extends BaseEntity
{
	private String name;
	
	@OneToMany
	private List<Product> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addToProducts(Product p)
	{
		List<Product> pl = getProducts();
		if(pl == null)
		{
			setProducts(new ArrayList<Product>());
			pl = getProducts();
		}
		pl.add(p);
	}
	
	public void removeFromProducts(Product p)
	{
		List<Product> pl = getProducts();
		if(pl == null)
		{
			setProducts(new ArrayList<Product>());
			pl = getProducts();
		}
		pl.remove(p);
	}
}
