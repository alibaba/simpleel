package com.alibaba.simpleEL.dialect.tiny;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyLocalVarDeclareStatement;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitorAdapter;

class TinyELPreprocessorContextDetectVisitor extends TinyELAstVisitorAdapter {
	/**
	 * 
	 */
	private final Map<String, Object> localVariants = new HashMap<String, Object>();
	private final Set<String> usedContextVariants = new HashSet<String>();
	private final Set<String> modifiedContextVariants = new HashSet<String>();

	public TinyELPreprocessorContextDetectVisitor() {
	}

	public Set<String> getUsedContextVariants() {
		return usedContextVariants;
	}
	
	public Set<String> getModifiedContextVariants() {
		return modifiedContextVariants;
	}

	@Override
	public boolean visit(TinyLocalVarDeclareStatement x) {
		for (int i = 0, size = x.getVariants().size(); i < size; ++i) {
			String varName = x.getVariants().get(i).getName();
			localVariants.put(varName, x.getType());
		}

		return super.visit(x);
	}
	
	@Override
	public boolean visit(TinyELIdentifierExpr x) {
		String ident = x.getName();

		if (!localVariants.containsKey(ident)) {
			usedContextVariants.add(ident);
		}
		
		return super.visit(x);
	}

	@Override
	public boolean visit(TinyELBinaryOpExpr x) {
		if (x.getLeft() instanceof TinyELIdentifierExpr) {
			switch (x.getOperator()) {
			case AddAndAssignment:
			case SubtractAndAssignment:
			case Assignment:
				String varName = ((TinyELIdentifierExpr) x.getLeft()).getName();
				if (!localVariants.containsKey(varName)) {
					modifiedContextVariants.add(varName);
				}
				break;
			default:
				break;
			}
		}
		return super.visit(x);
	}
}