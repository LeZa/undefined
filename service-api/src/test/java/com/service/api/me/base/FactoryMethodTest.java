package com.service.api.me.base;

import com.build.thinkingc.out.self.base.pattern.factory.factoryMethod.NYPizzaStore;
import com.build.thinkingc.out.self.base.pattern.factory.factoryMethod.PizzaStore;
import org.junit.Test;

import com.build.thinkingc.out.self.base.pattern.factory.pb.Pizza;

public class FactoryMethodTest {

	@Test
	public void factoryMethod(){
		PizzaStore nYPizzaStore =  new NYPizzaStore();
		Pizza pizza = nYPizzaStore.orderPizza("cheese");
		System.out.println(pizza);
	}
}
