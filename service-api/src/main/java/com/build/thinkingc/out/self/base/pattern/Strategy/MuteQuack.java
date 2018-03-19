package com.build.thinkingc.out.self.base.pattern.Strategy;

import com.build.thinkingc.out.self.base.pattern.Strategy.inter.QuackBehavior;

public class MuteQuack
		implements QuackBehavior{

	@Override
	public void quack() {
		System.out.println(" I can muteQuack.");
	}

}
