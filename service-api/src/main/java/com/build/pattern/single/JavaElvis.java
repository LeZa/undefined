package com.build.pattern.single;

public class JavaElvis {

	public static JavaElvis instance;
	
	private JavaElvis(){}
	
	public static JavaElvis getInstancce(){
		if( instance == null ){
			return instance =  new JavaElvis();
		}
		return instance;
	}
	
}
