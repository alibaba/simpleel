package com.alibaba.simpleEL.preprocess;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.ELException;

public class DefaultVariantResolver implements VariantResolver {
	private final Map<String, Type> variants = new HashMap<String, Type>();

	@Override
	public String resolve(String variant) {
		Type type = variants.get(variant);
		
		if (type == null) {
			throw new ELException("unkown variant : " + variant);
		}
		
		switch (type) {
		case Boolean:
			return "_bool(ctx.get(\"" + variant + "\"))";
		case String:
			return "_string(ctx.get(\"" + variant + "\"))";
		case Byte:
			return "_byte(ctx.get(\"" + variant + "\"))";
		case Short:
			return "_short(ctx.get(\"" + variant + "\"))";
		case Integer:
			return "_int(ctx.get(\"" + variant + "\"))";
		case Long:
			return "_long(ctx.get(\"" + variant + "\"))";
		case Float:
			return "_float(ctx.get(\"" + variant + "\"))";
		case Double:
			return "_double(ctx.get(\"" + variant + "\"))";
		case BigInteger:
			return "_bigInt(ctx.get(\"" + variant + "\"))";
		case BigDecimal:
			return "_decimal(ctx.get(\"" + variant + "\"))";
		case Date:
			return "_string(ctx.get(\"" + variant + "\"))";
		case Object:
			return "ctx.get(\"" + variant + "\")";
		default:
			throw new ELException("unkown variant : " + variant);
		}
	}
	
	public void registerVariant(String varaint, Type type) {
		if (varaint == null) {
			throw new IllegalArgumentException("varaint is null");
		}
		
		if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		
		variants.put(varaint, type);
	}
	
	public static enum Type {
		Boolean, String, Byte, Short, Integer, Long, Float, Double, BigInteger, BigDecimal, Date, Object
	}
}
