package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

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
		if (selectList != null) {
			this.selectList.output(buf);
			buf.append(" ");
		}

		if (where != null) {
			where.output(buf);
			buf.append(" ");
		}

		if (this.orderBy != null) {
			buf.append(" ");
			this.orderBy.output(buf);
		}
		
		if (this.limit != null) {
			buf.append(" ");
			this.limit.output(buf);
		}
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
