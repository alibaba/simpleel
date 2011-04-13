package com.alibaba.simpleEL.dialect.ql.ast;

public class QLSelect {
	private QLSelectList selectList;
	private QLExpr where;
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

	private QLOrderBy orderBy;

	public QLSelectList getSelectList() {
		return selectList;
	}

	public void setSelectList(QLSelectList selectList) {
		this.selectList = selectList;
	}
	
	

}
