package com.build.thinkingc.out.self.base.pattern.state;

import com.build.thinkingc.out.self.base.pattern.state.pb.GumballMachineII;

public class NoQuarterState implements State {
	
	GumballMachineII gm;
	
	public NoQuarterState( GumballMachineII gm){
		this.gm =  gm;
	}

	@Override
	public void insertQuarter() {
		System.out.println(" You inserted a quarter.");
		gm.setState(gm.getHasQuarterState());
		
	}

	@Override
	public void ejectQuarter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnCrank() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispense() {
		// TODO Auto-generated method stub
		
	}

}
