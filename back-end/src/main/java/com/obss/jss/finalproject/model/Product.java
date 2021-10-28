package com.obss.jss.finalproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Product extends BaseEntity
{
	@NotNull
	private String name;
	private Double price;
	private Integer stok;
	@ManyToOne
	private Seller seller;

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStok() {
		return stok;
	}
	public void setStok(Integer stok) {
		this.stok = stok;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
