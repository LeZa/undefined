package com.build.pattern.factory.simpleFactory;

import com.build.pattern.factory.pb.Pizza;

public class PizzaStore {

	private SimplePizzaFactory spf;
	
	public PizzaStore( SimplePizzaFactory spf){
		this.spf = spf;
	}
	
	public Pizza orderPizza(){
		
		Pizza  pizza = null;
		pizza = spf.createPizza("greek");
		if( pizza != null){
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();
		}
		return pizza;
	}
}
