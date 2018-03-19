package com.build.thinkingc.out.self.base.pattern.decorate;

import com.build.thinkingc.out.self.base.pattern.decorate.pb.Beverage;

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
