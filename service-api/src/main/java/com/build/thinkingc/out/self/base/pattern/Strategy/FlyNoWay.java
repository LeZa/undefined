package com.build.thinkingc.out.self.base.pattern.Strategy;

import com.build.thinkingc.out.self.base.pattern.Strategy.inter.FlyBehavior;

public class FlyNoWay 
	implements FlyBehavior{

	@Override
	public void fly() {
		System.out.println(" Sorry I can't fly.");
	}

}
