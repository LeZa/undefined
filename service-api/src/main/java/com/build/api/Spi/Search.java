package com.build.api.Spi;

import javax.print.Doc;
import java.util.List;

public interface Search {
	List<Doc> search(String keyword);
}
