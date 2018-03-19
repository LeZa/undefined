package com.build.thinkingc.out.self.base.Spi;

import javax.print.Doc;
import java.util.List;

public class DatabaseSearch
	implements Search{

	 @Override  
	    public List<Doc> search(String keyword) {
	        System.out.println("now use database search. keyword:" + keyword);  
	        return null;  
	    }  
}
