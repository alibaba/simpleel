package com.alibaba.simpleEL.dialect.ql.ast;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLSelectList extends QLAstNode {
	private final List<QLSelectItem> items = new ArrayList<QLSelectItem>();

	@Override
	protected void accept0(QLAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, this.items);
		}

		visitor.endVisit(this);
	}

	@Override
	public void output(StringBuffer buf) {
		if (items.size() == 0) {
			return;
		}
		
		buf.append("SELECT ");
		for (int i = 0, size = items.size(); i < size; ++i) {
			if (i != 0) {
				buf.append(", ");
			}
			
			items.get(i).output(buf);
		}
	}

}
