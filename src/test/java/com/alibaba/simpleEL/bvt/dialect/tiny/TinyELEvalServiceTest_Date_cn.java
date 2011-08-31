package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TinyELEvalServiceTest_Date_cn extends TestCase {
	public void test_0() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(Date.class, "出生日期");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse("2011-08-31 00:00:00");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("出生日期", date);

		Assert.assertEquals(true, service.eval(ctx, "出生日期 == \"2011-08-31 00:00:00\""));
	}


}
