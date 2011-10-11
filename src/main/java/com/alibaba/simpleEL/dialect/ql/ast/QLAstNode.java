package com.alibaba.simpleEL.dialect.ql.ast;

import java.util.List;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public abstract class QLAstNode {
	public final void accept(QLAstVisitor visitor) {
		if (visitor == null) {
			throw new IllegalArgumentException();
		}

		visitor.preVisit(this);

		accept0(visitor);

		visitor.postVisit(this);
	}

	protected abstract void accept0(QLAstVisitor visitor);

	protected final void acceptChild(QLAstVisitor visitor,
			List<? extends QLAstNode> children) {
		for (QLAstNode child : children)
			acceptChild(visitor, child);
	}

	protected final void acceptChild(QLAstVisitor visitor, QLAstNode child) {
		if (child == null) {
			return;
		}

		child.accept(visitor);
	}
	
	public abstract void output(StringBuffer buf);
	
	public String toString() {
	    StringBuffer buf = new StringBuffer();
	    output(buf);
	    return buf.toString();
	}
}
