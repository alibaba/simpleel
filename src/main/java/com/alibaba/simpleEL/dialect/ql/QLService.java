package com.alibaba.simpleEL.dialect.ql;

import java.util.Collection;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.parser.QLSelectParser;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class QLService {
	private DefaultExpressEvalService evalService = new DefaultExpressEvalService();
	
	public <T> void select(Class<T> clazz, Collection<T> srcCollection, Collection<T> destCollection, String expr) {
		QLSelectParser parser = new QLSelectParser(expr);
		
		QLSelect select = parser.select();
		
		throw new ELException("TODO");
	}
}
