package com.build.pattern.state;

import com.build.pattern.state.pb.GumballMachineII;

public class SoldState 
		implements State{
	
	GumballMachineII gm;
	
	public SoldState( GumballMachineII gm){
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
