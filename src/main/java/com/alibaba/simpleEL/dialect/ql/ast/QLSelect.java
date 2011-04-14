package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLSelect extends QLAstNode {
	private QLSelectList selectList;
	private QLExpr where;
	private QLOrderBy orderBy;
	
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

    public void output(StringBuffer buf) {
    	if (selectList != null) {
    		this.selectList.output(buf);
    		buf.append(" ");
    	}
    	
    	if (where != null) {
    		where.output(buf);
    		buf.append(" ");
    	}

        if (this.orderBy != null)  {
        	this.orderBy.output(buf);
        }
    }

    protected void accept0(QLAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.selectList);
            acceptChild(visitor, this.where);
            acceptChild(visitor, this.orderBy);
        }

        visitor.endVisit(this);
    }

}
