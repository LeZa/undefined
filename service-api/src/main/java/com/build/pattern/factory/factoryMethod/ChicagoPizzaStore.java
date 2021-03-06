package com.build.pattern.factory.factoryMethod;

import com.build.pattern.factory.pb.Pizza;

public class ChicagoPizzaStore extends PizzaStore {

	@Override
	Pizza createPizza(String type) {
		// TODO Auto-generated method stub
		if( type.equals("cheese") ){
			return new ChicagoStyleCheesePizza();
		}else if( type.equals("clam") ){
			return new ChicagoStyleClamPizza();
		}
		return null;
	}

}
