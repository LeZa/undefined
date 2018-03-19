package com.build.thinkingc.out.self.base.pattern.factory.factoryMethod;

import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

public class NYStyleCheesePizza  extends Pizza{

	public NYStyleCheesePizza(){
		name = " NY Style Sauce and Cheese Pizza";
		dough = " Thin Crust Dough";
		sauce = " Marinara Sauce";
//		toppings.add("Grated Reggiano Cheese");
		
	}
	
	@Override
	public void prepare() {
		System.out.println("NYStyle cheese Pizza prepare.");
	}

	@Override
	public void bake() {
		System.out.println("NYStyle cheese Pizza bake.");
	}

	@Override
	public void cut() {
		System.out.println("NYStyle cheese Pizza cut.");
	}

	@Override
	public void box() {
		System.out.println("NYStyle cheese Pizza box.");
	}

}
