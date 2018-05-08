package com.build.pattern.state;

import com.build.pattern.state.pb.GumballMachineI;

//状态模式
public class StateTest {

	public static void main( String sck[] ){
		StateTest tt =  new StateTest();
		tt.gumballMachine();
	}
	
	public void gumballMachine(){
		GumballMachineI gm = new GumballMachineI(5);
		System.out.println(gm);
		
		gm.insertQuarter();//插入硬币
		gm.turnCrank();//摇动曲柄
		System.out.println("------------------------------------------------------------");
		
		gm.insertQuarter();
		gm.ejectQuarter();//退还硬币
		gm.turnCrank();
		System.out.println("-------------------------------------------------------------");
		
		gm.insertQuarter();
		gm.turnCrank();
		gm.insertQuarter();
		gm.turnCrank();
		gm.ejectQuarter();
		System.out.println("-------------------------------------------------------------");
		
		gm.insertQuarter();
		gm.insertQuarter();
		gm.turnCrank();
		gm.insertQuarter();
		gm.turnCrank();
		gm.insertQuarter();
		gm.turnCrank();
		
	}
}
