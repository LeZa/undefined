package com.build.thinkingc.out.self.base.pattern.decorate;

import com.build.thinkingc.out.self.base.pattern.decorate.pb.Beverage;

//装饰模式  动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更有弹性的替代方案
public class DecorateTest {

	public static void main( String sck[] ){
		
		Beverage beverage =  new Espresso();
		System.out.println(beverage.getDescription() + "$ " + beverage.cost());
		
		Beverage beverage2 = new DarkRoast();
		beverage2 =  new Mocha(beverage2);
		System.out.println( beverage2.getDescription() + "$" + beverage2.cost());
		
		Beverage beverage3 =  new HouseBlend();
		beverage3 =  new Mocha( beverage3 );
		beverage3 =  new Whip( beverage3 );
		System.out.println( beverage3.getDescription() + "$" + beverage3.cost());
	}
}
