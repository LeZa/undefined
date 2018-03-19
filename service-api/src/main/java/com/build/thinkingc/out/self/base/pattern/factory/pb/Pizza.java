package com.build.thinkingc.out.self.base.pattern.factory.pb;

//import java.util.ArrayList;

public abstract class Pizza {
   public  String name;
   public String dough;//n. 	生面团; 
   public String sauce;//酱汁; 调味汁;
    
//   public ArrayList toppings = new ArrayList();
	//准备
	public  void prepare(){
		
	};
		
	//烘烤
	public  void bake(){};
	
	//切割
	public  void cut(){};
	
	//装箱
	public   void box(){};
	
	public String getName(){
		return name;
	}
	
	public String getDough(){
		return dough;
	}
	
	public String getSauce(){
		return sauce;
	}
	public String toString(){
		return name+dough+sauce;
	}
}
