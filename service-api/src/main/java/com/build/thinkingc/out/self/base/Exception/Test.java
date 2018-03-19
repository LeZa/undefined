package com.build.thinkingc.out.self.base.Exception;

import java.io.PrintWriter;
import java.io.StringWriter;


public class Test {
	
	private static void test1() throws NullPointerException{
		throw  new NullPointerException();
	}
	
	public static void main(String[] sck){
		try {
			Test.test1();
		} catch (NullPointerException e) {
			StringWriter trace = new StringWriter();
			e.printStackTrace(new PrintWriter(trace));
			EnumRecorder.Exception.recorder(trace.toString());
		}
	}
}
