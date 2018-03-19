package com.build.thinkingc.out.self.base.File;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class ConvertCharset {

	/**
	 * @param path
	 *            the file path or directory
	 * @param back
	 *            the backup file path or directory
	 * @param cs1
	 *            the orignle charset name
	 * @param cs2
	 *            the dest charset to convert
	 */
	
	public static void convertCharset(String path, String back, String cs1, String cs2) {
		try {
			String separator = System.getProperty("line.separator");
			File dir = new File(path);
			// a directory, not a file
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					File dirt = files[i];
					String fn = dirt.getName();
					String name = path + File.separator + fn;

					// recursion
					if (dirt.isDirectory()) {
						convertCharset(name, back + File.separator + fn, cs1, cs2);
					} else {
						// write
						File dest = new File(back);
						if (!dest.exists()) {
							dest.mkdirs();
						}
						FileOutputStream fos = new FileOutputStream(back + File.separator + fn);
						OutputStreamWriter osw = new OutputStreamWriter(fos, cs2);
						BufferedWriter bw = new BufferedWriter(osw);
						FileInputStream fis = new FileInputStream(name);
						InputStreamReader isr = new InputStreamReader(fis, cs1);
						BufferedReader br = new BufferedReader(isr);
						String line = null;
						while (null != (line = br.readLine())) {
							bw.write(line + separator);
						}
						fos.flush();
						osw.flush();
						bw.flush();
						bw.close();
						osw.close();
						fos.close();

						br.close();
						isr.close();
						fis.close();
					}
				}
			}else {
				// write
				File dest = new File(path);
				if (!dest.exists()) {
					dest.mkdir();
				}
				FileOutputStream fos = new FileOutputStream(back + File.separator + dir.getName());
				OutputStreamWriter osw = new OutputStreamWriter(fos, cs2);
				BufferedWriter bw = new BufferedWriter(osw);

				// read
				FileInputStream fis = new FileInputStream(path);
				InputStreamReader isr = new InputStreamReader(fis, cs1);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while (null != (line = br.readLine())) {
					bw.write(line + separator);
				}
				// write
				fos.flush();
				osw.flush();
				bw.flush();
				bw.close();
				osw.close();
				fos.close();

				// read
				br.close();
				isr.close();
				fis.close();
			}
			System.out.println("Convert Success !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	 public static void encoding(String toCharset, String path) throws Exception{
		  File srcFile = new File(path);
		  // ������ͬ������ת��

		  InputStream in = new FileInputStream(path);

		  BufferedReader br = new BufferedReader(
		    new InputStreamReader(in, "UTF-8"));

		  StringBuffer sb = new StringBuffer();
		  String s1;
		  while ((s1=br.readLine())!=null) {
		   String s = URLEncoder.encode(s1, toCharset);
		   sb.append(s+"\r\n");//һ��+�س�
		  }

		  br.close();
		  srcFile.delete();//ɾ��ԭ���ļ�
		  //�������±���д���ļ�������ֵ
		  File newfile = new File(path);//���½�ԭ�����ļ�
		  newfile.createNewFile();
		  OutputStream out = new FileOutputStream(newfile);
		  OutputStreamWriter  writer = new OutputStreamWriter(out, toCharset);
		  BufferedWriter bw = new BufferedWriter(writer);
		  bw.write(URLDecoder.decode(sb.toString(), toCharset));
		  bw.flush();//ˢ���ļ���
		  bw.close();
		 }
	
	
	public static void testFileCode() throws IOException{
		File file = new File("/home/centOS/file/tq1.txt");
		 BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));  
				    int p = (bin.read() << 8) + bin.read();  
				    String code = null;  
				      
				    switch (p) {  
				        case 0xefbb:  
				            code = "UTF-8";  
				            break;  
				        case 0xfffe:  
				            code = "Unicode";  
				            break;  
				        case 0xfeff:  
				            code = "UTF-16BE";  
				            break;  
				        default:  
				            code = "GBK";  
				    }  
		System.out.println(code);
		
	}
	
	
	public static void writeToFile( String fileName,String writeTxt) throws IOException{
		
		try {
			File f = new File(fileName);
			if (!f.exists()) {
			f.createNewFile();
			}
			  OutputStream out=new FileOutputStream(fileName);  
			OutputStreamWriter write = new OutputStreamWriter(out,"UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(writeTxt);
			writer.close();
			} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		// a single file
//		convertCharset("F:\\workspace\\Mao\\downFile\\todoxq.txt",
//				"F:\\workspace\\Mao\\downFile\\todo", "UTF-8", "UTF-8");
		
		try {
//			  encoding("UTF-8","F:\\workspace\\Mao\\downFile\\todoxq.txt");
			testFileCode();
			
//			writeToFile("/home/centOS/file/tq.txt","曹玮琪大傻B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// not a file , it's a directory
		// convertCharset ("I:\\2013313workspace\\Yaojiangji\\src\\yaojiangji",
		// "I:\\2013313workspace\\Yaojiangji\\back\\yaojiangji",
		// "GBK",
		// "UTF-8");
	}

}
