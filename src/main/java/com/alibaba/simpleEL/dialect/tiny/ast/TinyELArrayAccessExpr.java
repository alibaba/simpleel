package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELArrayAccessExpr extends TinyELExpr {
	private TinyELExpr array;
	private TinyELExpr index;

	public TinyELArrayAccessExpr() {

	}

	public TinyELArrayAccessExpr(TinyELExpr array, TinyELExpr index) {
		this.array = array;
		this.index = index;
	}

	public TinyELExpr getArray() {
		return array;
	}

	public void setArray(TinyELExpr array) {
		this.array = array;
	}

	public TinyELExpr getIndex() {
		return index;
	}

	public void setIndex(TinyELExpr index) {
		this.index = index;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			array.acceptChild(visitor, array);
			array.acceptChild(visitor, index);
		}
		visitor.endVisit(this);
	}

	@Override
	public void output(StringBuffer buf) {
		array.output(buf);
		buf.append("[");
		index.output(buf);
		buf.append("]");
	}

}
