package com.mycom.DemoHib;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Desktop {
	@Id
	private int desktopId;
	private String brand;
	private int price;
	@ManyToOne
	private User user;
	
	public int getDesktopId() {
		return desktopId;
	}
	public void setDesktopId(int desktopId) {
		this.desktopId = desktopId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Desktop [desktopId=" + desktopId + ", brand=" + brand + ", price=" + price + "]";
	}
	
	
}
