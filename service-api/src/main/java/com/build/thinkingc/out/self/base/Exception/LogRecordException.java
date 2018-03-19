package com.build.thinkingc.out.self.base.Exception;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LogRecordException extends Exception {

	private static final long serialVersionUID = 5093841389340386493L;

	private static Logger log = Logger.getLogger("LogRecordException");
	private Throwable cause;

	public LogRecordException() {
	};

	public LogRecordException(String msg) {
		super(msg);
	}

	public String getMessage() {
		return super.getMessage();
	}

	public void printStackTrace(Class<?> cla, String content, PrintStream ps) {
		
		ps.println();
//		try {
//			BufferedInputStream in = new BufferedInputStream(new FileInputStream("Redirecting.java"));
//			System.setIn(in);
//			System.setOut(ps);
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			String s;
//
//			while ((s = br.readLine()) != null)
//				System.out.println(s);
//			ps.close(); // Remember this!
//		} catch (IOException e) {
//		}

		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>printStackTrace");
		// try {
		// BufferedInputStream in = new BufferedInputStream( new
		// FileInputStream(cla.getName()));
		// BufferedReader br = new BufferedReader( new InputStream(ps));
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// if (cause == null) {
		// super.printStackTrace(ps);
		// } else {
		// ps.println(this);
		// cause.printStackTrace(ps);
		// }
	}

	static void recordException(Exception exp) {
		StringWriter sw = new StringWriter();
		exp.printStackTrace();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(sw.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		log.severe(sw.toString());
	}

}
