package com.build.api.File;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

public class ZipDesmie {

	public static void main(String sck[]) throws IOException{
//		ZipDesmie.extractRarFiles();
//		ZipDesmie.unZipFiles("/home/centOS/file/fex.zip", "/home/centOS/fi/");
	}
	
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * ��ѹ�ļ���ָ��Ŀ¼
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// �ж�·���Ƿ����,�������򴴽��ļ�·��
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// ����ļ�·����Ϣ
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************��ѹ���********************");
	}

	
	 public static  void extractRarFiles() {  
	        String filename = "f:/kex.rar";  
	        File archive = new File(filename);  
	        File destination = new File("d:/jar");  
	  
	        Archive arch = null;  
	        try {  
	            arch = new Archive(archive);  
	        } catch (RarException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        if (arch != null) {  
	            if (arch.isEncrypted()) {  
	                System.out.println("archive is encrypted cannot extreact");  
	                return;  
	            }  
	            FileHeader fh = null;  
	            while (true) {  
	                fh = arch.nextFileHeader();  
	                if (fh == null) {  
	                    break;  
	                }  
	                if (fh.isEncrypted()) {  
	                    System.out.println("file is encrypted cannot extract: "  
	                            + fh.getFileNameString());  
	                    continue;  
	                }  
	                System.out.println("extracting: " + fh.getFileNameString());  
	                try {  
	                    if (fh.isDirectory()) {  
	                        createDirectory(fh, destination);  
	                    } else {  
	                        File f = createFile(fh, destination);  
	                        OutputStream stream = new FileOutputStream(f);  
	                        arch.extractFile(fh, stream);  
	                        stream.close();  
	                    }  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
	
	 private static File createFile(FileHeader fh, File destination) {  
	        File f = null;  
	        String name = null;  
	        if (fh.isFileHeader() && fh.isUnicode()) {  
	            name = fh.getFileNameW();  
	        } else {  
	            name = fh.getFileNameString();  
	        }  
	        f = new File(destination, name);  
	        if (!f.exists()) {  
	            try {  
	                f = makeFile(destination, name);  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return f;  
	    }  
	 private static File makeFile(File destination, String name) throws IOException {  
	        String[] dirs = name.split("\\\\");  
	        if (dirs == null) {  
	            return null;  
	        }  
	        String path = "";  
	        int size = dirs.length;  
	        if (size == 1) {  
	            return new File(destination, name);  
	        } else if (size > 1) {  
	            for (int i = 0; i < dirs.length - 1; i++) {  
	                path = path + File.separator + dirs[i];  
	                new File(destination, path).mkdir();  
	            }  
	            path = path + File.separator + dirs[dirs.length - 1];  
	            File f = new File(destination, path);  
	            f.createNewFile();  
	            return f;  
	        } else {  
	            return null;  
	        }  
	    }
	 
	 
	    private  static void createDirectory(FileHeader fh, File destination) {  
	        File f = null;  
	        if (fh.isDirectory() && fh.isUnicode()) {  
	            f = new File(destination, fh.getFileNameW());  
	            if (!f.exists()) {  
	                makeDirectory(destination, fh.getFileNameW());  
	            }  
	        } else if (fh.isDirectory() && !fh.isUnicode()) {  
	            f = new File(destination, fh.getFileNameString());  
	            if (!f.exists()) {  
	                makeDirectory(destination, fh.getFileNameString());  
	            }  
	        }  
	    }  
	    
	    
	    private static void makeDirectory(File destination, String fileName) {  
	        String[] dirs = fileName.split("\\\\");  
	        if (dirs == null) {  
	            return;  
	        }  
	        String path = "";  
	        for (String dir : dirs) {  
	            path = path + File.separator + dir;  
	            new File(destination, path).mkdir();  
	        }  
	    }  
}
