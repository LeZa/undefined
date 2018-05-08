package com.build.pattern.Strategy;

import com.build.pattern.Strategy.inter.FlyBehavior;

public class FlyWithWings 
	implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println(" I can fly.");
	}

}
