package com.build.thinkingc.out.self.base.Spi;

import javax.print.Doc;
import java.util.List;

public interface Search {
	List<Doc> search(String keyword);
}
