package com.build.pattern.factory.abstractFactory;

import com.build.pattern.factory.abstractFactory.NYPizzaIngredient.FreshClams;
import com.build.pattern.factory.abstractFactory.NYPizzaIngredient.MarinaraSauce;
import com.build.pattern.factory.abstractFactory.NYPizzaIngredient.ReggianoCheese;
import com.build.pattern.factory.abstractFactory.NYPizzaIngredient.SlicedPepperoni;
import com.build.pattern.factory.abstractFactory.NYPizzaIngredient.ThinCrustDough;
import com.build.pattern.factory.pb.Cheese;
import com.build.pattern.factory.pb.Clams;
import com.build.pattern.factory.pb.Dough;
import com.build.pattern.factory.pb.Pepperoni;
import com.build.pattern.factory.pb.Sauce;
import com.build.pattern.factory.pb.Veggies;
import com.build.pattern.factory.pb.Veggie.Garlic;
import com.build.pattern.factory.pb.Veggie.Onion;

public class NYPizzaIngredientFacotry 
	implements PizzaIngredientFactory{

	@Override
	public Dough createDough() {
		return new ThinCrustDough();
	}

	@Override
	public Sauce createSauce() {
		return new MarinaraSauce();
	}

	@Override
	public Cheese createChese() {
		return new ReggianoCheese();
	}

	@Override
	public Veggies[] createVeggies() {
		Veggies[] vegs =  new Veggies[2];
		vegs[0] =  new Garlic();
		vegs[1] = new Onion();
		return vegs;
	}

	@Override
	public Pepperoni createPepperoni() {
		return new  SlicedPepperoni();
	}

	@Override
	public Clams createClam() {
		return new FreshClams();
	}

}
