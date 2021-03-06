package com.build.pattern.decorate;

import com.build.pattern.decorate.pb.Beverage;
import com.build.pattern.decorate.pb.CondimentDecortor;

public class Whip
	extends CondimentDecortor{

	Beverage beverage;
	
	public Whip( Beverage beverage ){
		this.beverage = beverage;
	}
	
	@Override
	public String getDescription() {
		return beverage.getDescription() + ",Whip ";
	}

	@Override
	public double cost() {
		return .89 + beverage.cost();
	}

}
