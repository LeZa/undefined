package com.build.thinkingc.out.self.base.pattern.state;

import com.build.thinkingc.out.self.base.pattern.state.pb.GumballMachineII;

public class HasQuarterState
		implements State{
	
	GumballMachineII gm;
	
	public HasQuarterState( GumballMachineII gm){
		this.gm = gm;
	}

	@Override
	public void insertQuarter() {
		// TODO Auto-generated method stub
		
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
