package com.build.thinkingc.out.self.base.Exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionRecorder {

	static void logException(Class<?> cal,Exception e) {
		StringWriter trace = new StringWriter();
		e.printStackTrace(new PrintWriter(trace));
		System.out.println(cal.getName()+">>>>>>>>>>>>>"+trace.toString());
//		logger.severe(trace.toString());
	}

}
