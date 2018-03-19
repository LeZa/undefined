package com.build.thinkingc.out.self.base.api;

/*
 * static 关键字 有类有关，与对象无关
 */
public class StaticWord {

	private int id;
	private static int nextId = 1;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static int getNextId() {
		return nextId;
	}
	public static void setNextId(int nextId) {
		StaticWord.nextId = nextId;
	}
	
	
	
	public static void main( String sck[] ){
		
		StaticWord sw1 = new StaticWord();
		System.out.println(sw1.getNextId());
		sw1.setNextId(2);
		sw1.setId(23);
		System.out.println(sw1.getId());
		StaticWord sw2 = new StaticWord();
		System.out.println(sw2.getNextId());
		System.out.println(sw2.getId());
		
		
		StaticWord sw3 = new StaticWord();
		System.out.println(sw3.getNextId());
	}
}
