package com.build.thinking.in.java.exceptions;


/**
 * @Description 测试文件读取完后关闭文件流
 */
public class Cleanup {
  public static void main(String[] args) {
    try {
      InputFile in = new InputFile(
              "/home/ubuntu/ideaProjects/undefined/service-api/src/main/java/com/build/thinking/in/java/exceptions/Cleanup.java");
      try {
        String s;
        int i = 1;
        while((s = in.getLine()) != null)
          ; // Perform line-by-line processing here...
      } catch(Exception e) {
        System.out.println("Caught Exception in main");
        e.printStackTrace(System.out);
      } finally {
        in.dispose();
      }
    } catch(Exception e) {
      System.out.println("InputFile construction failed");
    }
  }
} /* Output:
dispose() successful
*///:~
