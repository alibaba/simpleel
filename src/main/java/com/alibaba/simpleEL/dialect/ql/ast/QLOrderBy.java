package com.alibaba.simpleEL.dialect.ql.ast;

import java.util.ArrayList;
import java.util.List;

public class QLOrderBy {
	private final List<QLOrderByItem> items = new ArrayList<QLOrderByItem>();
	
	public List<QLOrderByItem> getItems() {
		return this.items;
	}
}
