package com.alibaba.simpleEL.preprocess;

import java.util.HashSet;
import java.util.Set;

public class DefaultVariantResolver implements VariantResolver {
	private Set<String> stringVariants = new HashSet<String>();

	@Override
	public String resolve(String variant) {
		if (isStringVariant(variant)) {
			return "_string(ctx.get(\"" + variant + "\"))";
		}
		return null;
	}

	public boolean isStringVariant(String variant) {
		return stringVariants.contains(variant);
	}
}
