package com.build.pattern.Strategy;

import com.build.pattern.Strategy.inter.QuackBehavior;

public class Quack 
		implements QuackBehavior{

	@Override
	public void quack() {
		System.out.println(" I can quack also.");
	}

}
