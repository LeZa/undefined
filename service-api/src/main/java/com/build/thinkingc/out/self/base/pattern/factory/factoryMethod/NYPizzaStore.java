package com.build.thinkingc.out.self.base.pattern.factory.factoryMethod;

import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

public class NYPizzaStore  extends PizzaStore{

	@Override
	Pizza createPizza(String type) {
		// TODO Auto-generated method stub
		if( type.equals("cheese") ){
			return new NYStyleCheesePizza();
		}else if( type.equals("veggie") ){
			return new NYStyleVeggiePizza();
		}
		return null;
	}

}
