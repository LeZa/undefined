package com.build.thinkingc.out.self.base.GC;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {

	static class OOMObject{
		
	}
	
	public static void main(String sck[]){
		List<OOMObject> list =  new ArrayList<OOMObject>();
		while(true){
			list.add(new OOMObject());
		}
		
	}
}
