package com.build.pattern.skeletal;


/*
 * 骨架实现
 * 	0:本田汽车
 * 	1:思域
 * 	2:普通思域
 *  3:思域si
 *  注释:思域si含有尾翼，尾翼的实现则在思域上而不是本田汽车上
 */
public class Skeletal {

}


interface  Honda{
	
	/*
	 * 一部车的所有零件
	 */
	void engine();
	
	void body();
	
	void wheel();
	
}

abstract  class Civic 
		implements Honda{
	
	public void engine(){
		
	}
	
	public void body(){
		
	}
	
	public void wheel(){
		
	}
	
	public void empennage(){
		
	}
	
}

class NormalCivic 
		extends Civic implements Honda{
	
	@Override
	public void engine(){
		
	}
	
	@Override
	public void body(){
		
	}
	
	@Override
	public void wheel(){
		
	}
}

class CivicSi 
	extends Civic implements Honda{
	
	
	@Override
	public void engine(){
		
	}
	
	@Override
	public void body(){
		
	}
	
	@Override
	public void wheel(){
		
	}
	
	@Override
	public void empennage(){
		
	}
}
