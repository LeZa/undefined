package com.build.pattern.decorate;

import com.build.pattern.decorate.pb.Beverage;

public class DarkRoast 
	extends Beverage{

	public DarkRoast(){
		description = "DarkRoast";
	}
	
	@Override
	public double cost(){
		return .99;
	}
}
