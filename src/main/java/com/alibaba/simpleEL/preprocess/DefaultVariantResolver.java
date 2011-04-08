package com.alibaba.simpleEL.preprocess;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.ELException;

public class DefaultVariantResolver implements VariantResolver {
	private final Map<String, Class<?>> variants = new HashMap<String, Class<?>>();

	@Override
	public String resolve(String variant) {
		Class<?> type = variants.get(variant);
		
		if (type == null) {
			throw new ELException("unkown variant : " + variant);
		}
		
		if (boolean.class == type) {
			return "_bool(ctx.get(\"" + variant + "\"))";
		} else if (String.class == type) {
			return "_string(ctx.get(\"" + variant + "\"))";
		} else if (byte.class == type) {
			return "_byte(ctx.get(\"" + variant + "\"))";
		} else if (short.class == type) {
			return "_short(ctx.get(\"" + variant + "\"))";
		} else if (int.class == type) {
			return "_int(ctx.get(\"" + variant + "\"))";
		} else if (long.class == type) {
			return "_long(ctx.get(\"" + variant + "\"))";
		} else if (float.class == type) {
			return "_float(ctx.get(\"" + variant + "\"))";
		} else if (double.class == type) {
			return "_double(ctx.get(\"" + variant + "\"))";
		} else if (BigInteger.class == type) {
			return "_bigInt(ctx.get(\"" + variant + "\"))";
		} else if (BigDecimal.class == type) {
			return "_decimal(ctx.get(\"" + variant + "\"))";
		} else if (java.util.Date.class == type) {
			return "_date(ctx.get(\"" + variant + "\"))";
		} else if (Object.class == type) {
			return "ctx.get(\"" + variant + "\")";
		} else {
			String className = type.getName();
			className = className.replaceAll("$", "."); // inner class
			return "((" + className + ")" + "ctx.get(\"" + variant + "\"))";
		}
	}
	
	public void registerVariant(Class<?> clazz, String... variants) {
		
		if (clazz == null) {
			throw new IllegalArgumentException("type is null");
		}
		
		for (String variant : variants) {
			if (variant == null) {
				throw new IllegalArgumentException("varaint is null");
			}
	
			this.variants.put(variant, clazz);
		}
	}
}
