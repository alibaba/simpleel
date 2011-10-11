package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;
import com.alibaba.simpleEL.dialect.ql.visitor.QLOutputAstVisitor;

public class QLSelect extends QLAstNode {
	private QLSelectList selectList;
	private QLExpr where;
	private QLOrderBy orderBy;
	private QLLimit limit;

	public QLExpr getWhere() {
		return where;
	}

	public void setWhere(QLExpr where) {
		this.where = where;
	}

	public QLOrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(QLOrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public QLSelectList getSelectList() {
		return selectList;
	}

	public void setSelectList(QLSelectList selectList) {
		this.selectList = selectList;
	}

	public QLLimit getLimit() {
		return limit;
	}

	public void setLimit(QLLimit limit) {
		this.limit = limit;
	}

	public void output(StringBuffer buf) {
        StringWriter out = new StringWriter();
        QLOutputAstVisitor visitor = new QLOutputAstVisitor(new PrintWriter(out));
        this.accept(visitor);
        buf.append(out.toString());
	}

	protected void accept0(QLAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, this.selectList);
			acceptChild(visitor, this.where);
			acceptChild(visitor, this.orderBy);
			acceptChild(visitor, this.limit);
		}

		visitor.endVisit(this);
	}

}
