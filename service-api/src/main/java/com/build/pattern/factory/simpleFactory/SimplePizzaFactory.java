package com.build.pattern.factory.simpleFactory;

import com.build.pattern.factory.II.CheesePizza;
import com.build.pattern.factory.II.PepperoniPizza;
import com.build.pattern.factory.III.ClamPizza;
import com.build.pattern.factory.III.VeggiePizza;
import com.build.pattern.factory.pb.Pizza;

public class SimplePizzaFactory {
	
	public Pizza createPizza( String type ){
		
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
		 return pizza;
	}

}
