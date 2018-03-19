package com.build.thinkingc.out.self.base.pattern.factory.simpleFactory;

import com.build.thinkingc.out.self.base.pattern.factory.II.CheesePizza;
import com.build.thinkingc.out.self.base.pattern.factory.II.PepperoniPizza;
import com.build.thinkingc.out.self.base.pattern.factory.III.ClamPizza;
import com.build.thinkingc.out.self.base.pattern.factory.III.VeggiePizza;
import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

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
