package com.service.api.me.base;

import com.build.thinkingc.out.self.base.pattern.Strategy.pb.Duck;
import com.build.thinkingc.out.self.base.pattern.Strategy.pb.MallardDuck;
import org.junit.Test;


//找出应用中可能需要变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起;
//策略模式 定义了算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户
public class StrategyTest {

	@Test
	public void mallardDuck(){
		Duck md =  new MallardDuck();
		
		md.performFly();
		md.performQuack();
		md.display();
		System.out.println("----------------------------------");
		md.setFlyBehavior(new com.build.thinkingc.out.self.base.pattern.Strategy.FlyNoWay());
		md.performFly();
		md.setQuackBehavior(new com.build.thinkingc.out.self.base.pattern.Strategy.MuteQuack());
		md.performQuack();
	}
}
