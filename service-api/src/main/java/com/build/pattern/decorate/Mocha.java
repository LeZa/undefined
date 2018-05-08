package com.build.pattern.decorate;

import com.build.pattern.decorate.pb.Beverage;
import com.build.pattern.decorate.pb.CondimentDecortor;

public class Mocha 
		extends CondimentDecortor{
	
	Beverage beverage;
	
	public Mocha( Beverage beverage ){
		this.beverage = beverage;
	}

	@Override
	public String getDescription() {
		return beverage.getDescription() + ",Mocha";
	}

	@Override
	public double cost() {
		return .20+beverage.cost();
	}

}
