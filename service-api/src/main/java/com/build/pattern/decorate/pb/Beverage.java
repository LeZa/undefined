package com.build.pattern.decorate.pb;

//beverage:饮料
public abstract class Beverage {

	public String description = " Unknown Beverage.";
	
	public String getDescription(){
		return description;
	}
	
	public abstract double cost();
}
