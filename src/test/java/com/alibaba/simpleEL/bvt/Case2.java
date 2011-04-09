package com.alibaba.simpleEL.bvt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class Case2 extends TestCase {
	public void test_0 () throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.setAllowMultiStatement(true);
		
		service.regsiterVariant(Person.class, "p");
		service.regsiterVariant(List.class, "list");
		
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(18, "吴能"));
		persons.add(new Person(27, "刘芒"));
		persons.add(new Person(40, "黄警"));
		persons.add(new Person(50, "夏留"));
		
		List<Person> list = new ArrayList<Person>();

		for (Person p : persons) {
			Map<String, Object> ctx = new HashMap<String, Object>();
	        ctx.put("p", p);
	        ctx.put("list", list);
	        service.eval(ctx, "if (@p.getAge() > 30) { @list.add(@p); } return null;");
		}
        
        Assert.assertEquals(2, list.size());
	}
	
	public static class Person {
		private int age;
		private String name;
		
		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
		
		public int getAge() { return age; }
		public String getName() { return name; }
	}
}


