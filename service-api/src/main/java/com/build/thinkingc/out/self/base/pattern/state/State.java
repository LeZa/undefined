package com.build.thinkingc.out.self.base.pattern.state;

public interface State {

	void insertQuarter();//投入硬币
	
	void ejectQuarter();//退还硬币
	
	void turnCrank();//摇动曲柄
	
	void dispense();//出糖果
}
