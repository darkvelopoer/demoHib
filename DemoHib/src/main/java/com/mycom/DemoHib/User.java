package com.mycom.DemoHib;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private int userId;
	private String name;
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private Collection<Desktop> desktops = new ArrayList<>();
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<Desktop> getDesktops() {
		return desktops;
	}
	public void setDesktops(Collection<Desktop> desktops) {
		this.desktops = desktops;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", desktops=" + desktops + "]";
	}
	
	
}
