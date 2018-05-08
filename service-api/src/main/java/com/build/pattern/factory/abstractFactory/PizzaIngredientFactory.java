package com.build.pattern.factory.abstractFactory;

import com.build.pattern.factory.pb.Cheese;
import com.build.pattern.factory.pb.Clams;
import com.build.pattern.factory.pb.Dough;
import com.build.pattern.factory.pb.Pepperoni;
import com.build.pattern.factory.pb.Sauce;
import com.build.pattern.factory.pb.Veggies;

//抽象工厂模式 提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类(要实现接口的子类去实现应该实现的具体类)
public interface PizzaIngredientFactory {

	public Dough createDough();
	
	public Sauce createSauce();
	
	public Cheese createChese();
	
	public Veggies[] createVeggies();
	
	public Pepperoni createPepperoni();
	
	public Clams createClam();
}
