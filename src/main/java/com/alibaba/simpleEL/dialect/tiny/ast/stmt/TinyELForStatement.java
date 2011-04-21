package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELForStatement extends TinyELStatement {
	private String type;

	private final List<TinyELVariantDeclareItem> variants = new ArrayList<TinyELVariantDeclareItem>();

	private TinyELExpr condition;

	private TinyELExpr postExpr;
	
	private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

	public TinyELForStatement() {

	}
	
	public List<TinyELStatement> getStatementList() {
		return statementList;
	}

	public TinyELExpr getCondition() {
		return condition;
	}

	public void setCondition(TinyELExpr condition) {
		this.condition = condition;
	}

	public TinyELExpr getPostExpr() {
		return postExpr;
	}

	public void setPostExpr(TinyELExpr postExpr) {
		this.postExpr = postExpr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TinyELVariantDeclareItem> getVariants() {
		return variants;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, variants);
			acceptChild(visitor, condition);
			acceptChild(visitor, postExpr);
			acceptChild(visitor, statementList);
		}
		visitor.endVisit(this);
	}

}
