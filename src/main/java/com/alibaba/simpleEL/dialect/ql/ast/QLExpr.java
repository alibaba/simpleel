package com.alibaba.simpleEL.dialect.ql.ast;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public abstract class QLExpr extends QLAstNode {
	private final Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	protected void accept0(QLAstVisitor visitor) {
		
	}

    public String toString() {
        StringBuffer buf = new StringBuffer();
        output(buf);
        return buf.toString();
    }
    
    public Map<String, Object> getAttributes() {
    	return this.attributes;
    }
}
