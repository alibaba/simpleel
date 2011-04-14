package com.alibaba.simpleEL.dialect.ql;

import java.util.Collection;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class QLService {
	private DefaultExpressEvalService evalService = new DefaultExpressEvalService();
	
	public <T> void select(Class<T> clazz, Collection<T> srcCollection, Collection<T> destCollection, String expr) {
		throw new ELException("TODO");
	}
}
