package com.build.pattern.Strategy;

import com.build.pattern.Strategy.inter.FlyBehavior;

public class FlyNoWay 
	implements FlyBehavior{

	@Override
	public void fly() {
		System.out.println(" Sorry I can't fly.");
	}

}
