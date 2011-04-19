package com.alibaba.simpleEL.dialect.tiny.ast;

import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public abstract class TinyELAstNode {
	public final void accept(TinyELAstVisitor visitor) {
		if (visitor == null) {
			throw new IllegalArgumentException();
		}

		visitor.preVisit(this);

		accept0(visitor);

		visitor.postVisit(this);
	}

	protected abstract void accept0(TinyELAstVisitor visitor);

	protected final void acceptChild(TinyELAstVisitor visitor,
			List<? extends TinyELAstNode> children) {
		for (TinyELAstNode child : children)
			acceptChild(visitor, child);
	}

	protected final void acceptChild(TinyELAstVisitor visitor, TinyELAstNode child) {
		if (child == null) {
			return;
		}

		child.accept(visitor);
	}
	
	public void output(StringBuffer buf) {
		
	}
}
