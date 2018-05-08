package com.build.pattern.factory.factoryMethod;

import com.build.pattern.factory.pb.Pizza;

public abstract class PizzaStore {
	
	public final Pizza orderPizza( String type ){
		Pizza pizza = null;
		pizza =  createPizza(type);
		
		if( pizza != null){
			pizza.prepare();
			pizza.bake();
			pizza.cut();
			pizza.box();
		}
		
		return pizza;
		
	}
	
	abstract Pizza createPizza( String type );
}
