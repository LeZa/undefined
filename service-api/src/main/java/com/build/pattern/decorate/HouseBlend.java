package com.build.pattern.decorate;

import com.build.pattern.decorate.pb.Beverage;

public class HouseBlend 
	extends Beverage{

	public HouseBlend(){
		description = "HouseBlend";
	}
	
	@Override
	public double cost() {
		return .89;
	}

}
