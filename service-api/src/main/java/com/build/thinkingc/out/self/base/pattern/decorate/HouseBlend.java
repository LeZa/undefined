package com.build.thinkingc.out.self.base.pattern.decorate;

import com.build.thinkingc.out.self.base.pattern.decorate.pb.Beverage;

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
