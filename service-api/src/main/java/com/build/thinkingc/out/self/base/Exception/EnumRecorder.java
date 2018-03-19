package com.build.thinkingc.out.self.base.Exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum EnumRecorder {

	Exception {

		public String splash;
		public String root = "downFile";
		FileOutputStream fos = null;
		OutputStreamWriter os = null;
		BufferedWriter bw = null;

		@Override
		void recorder(String record) {
			String day = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			if (System.getProperty("os.name") != null
					&& System.getProperty("os.name").toLowerCase().contains("windows")) {
				splash = "\\";
			} else {
				splash = "/";
			}
			String fileNameTemp = System.getProperty("user.dir") + "" + root + splash + day + "Exception.txt";
			File file = new File(fileNameTemp);
			try {
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					fos = new FileOutputStream(file);
					os = new OutputStreamWriter(fos, "utf-8");
					bw = new BufferedWriter(os);
					bw.write(URLDecoder.decode(record, "UTF-8"));
					bw.newLine();
				} else {
					file.getParentFile().mkdirs();
					fos = new FileOutputStream(file);
					os = new OutputStreamWriter(fos, "utf-8");
					bw = new BufferedWriter(os);
					bw.write(URLDecoder.decode(record, "UTF-8"));
					bw.newLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	abstract void recorder(String record);
}
