package com.build.thinkingc.out.self.base.pattern.factory.II;

import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

public class IIFirst {

	public Pizza orderPizza( String type ){
		Pizza pizza = null;
		 if( type.equals("cheese") ){
			 pizza = new CheesePizza();
		 }
		 if( type.equals("greek") ){
			 pizza  =  new GreekPizza();
		 }
		 if( type.equals("pepperoni") ){
			 pizza =  new PepperoniPizza();
		 }
		 
		 if( pizza != null){
			 pizza.prepare();
			 pizza.bake();
			 pizza.cut();
			 pizza.box();
		 }
		 return pizza;
	}
}
