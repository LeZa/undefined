package com.build.thinkingc.out.self.base.pattern.state.pb;

import com.build.thinkingc.out.self.base.pattern.state.HasQuarterState;
import com.build.thinkingc.out.self.base.pattern.state.NoQuarterState;
import com.build.thinkingc.out.self.base.pattern.state.SoldOutState;
import com.build.thinkingc.out.self.base.pattern.state.SoldState;
import com.build.thinkingc.out.self.base.pattern.state.State;

public class GumballMachineII {

	State soldOutState;
	State noQuarterState;
	State hasQuarterState;
	State soldState;
	
	State state = soldOutState;
	
	int count = 0;
	public GumballMachineII( int numberGumballs ){
		soldOutState = new SoldOutState(this);
		noQuarterState =  new NoQuarterState(this);
		hasQuarterState =  new HasQuarterState(this);
		soldState = new SoldState(this);
		this.count = numberGumballs;
		if( numberGumballs > 0 ){
			state = noQuarterState;
		}
	}
	
	public void insertQuarter(){
		state.insertQuarter();
	}
	
	public void ejectQuarter(){
		state.ejectQuarter();
	}
	
	public void turnCrank(){
		state.turnCrank();
		state.dispense();
	}
	
	public void setState( State state ){
		this.state = state;
	}
	
	void releaseBall(){
		System.out.println(" A gumball comes rolling out the slot...");
		if( count != 0 ){
			count = count -1;
		}
	}
		
	public State getNoQuarterState(){
			return this.noQuarterState;
	}
	
	public State getHasQuarterState(){
		return this.hasQuarterState;
	}
	
	public State getSoldOutState(){
		return this.soldOutState;
	}
	
	public State getSoldState(){
		return this.soldState;
	}
}
