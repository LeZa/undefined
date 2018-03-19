package com.build.thinkingc.out.self.base.pattern.factory.III;

import com.build.thinkingc.out.self.base.pattern.factory.II.CheesePizza;
//import base.pattern.factory.II.GreekPizza;
import com.build.thinkingc.out.self.base.pattern.factory.II.PepperoniPizza;
import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

public class IIIFirst {

	public Pizza orderPizza( String type ){
		Pizza pizza = null;
		
		if( type.equals("cheese") ){
			 pizza = new CheesePizza();
		 }
//		 if( type.equals("greek") ){
//			 pizza  =  new GreekPizza();
//		 }
		 if( type.equals("pepperoni") ){
			 pizza =  new PepperoniPizza();
		 }
		 if( type.equals("clam") ){
			 pizza =  new ClamPizza();
		 }
		 if( type.equals("veggie") ){
			 pizza =  new VeggiePizza();
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
