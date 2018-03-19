package com.build.thinkingc.out.self.base.pattern.decorate;

import com.build.thinkingc.out.self.base.pattern.decorate.pb.Beverage;

public class Espresso 
	extends Beverage{

	public Espresso(){
		description = "Espresso";
	}
	
	@Override
	public double cost(){
		return 1.99;
	}
}
