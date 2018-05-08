package com.build.pattern.Strategy.pb;

import com.build.pattern.Strategy.FlyWithWings;
import com.build.pattern.Strategy.Quack;

public class MallardDuck 
	extends Duck{

	public MallardDuck(){
		flyBehavior =  new FlyWithWings();
		quackBehavior = new Quack();
	}
	
	@Override
	public void display() {
		System.out.println(" I'm a real Mallard duck.");
	}

}
