package com.alibaba.simpleEL.dialect.ql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

public class QLServiceTest extends TestCase {
	public static class Person {
		private int age;
		private String name;

		public Person() {
		}

		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}

		public int getAge() { return age; }

		public void setAge(int age) { this.age = age;}

		public String getName() {return name;}

		public void setName(String name) {this.name = name;}
	}

	public void test_0 () throws Exception {
		QLEvalService service = new QLEvalService();
		
		List<Person> srcCollection = new ArrayList<Person>();
		srcCollection.add(new Person(18, "吴能"));
		srcCollection.add(new Person(27, "刘芒"));
		srcCollection.add(new Person(40, "黄警"));
		srcCollection.add(new Person(50, "夏留"));
		srcCollection.add(new Person(60, "刘晶"));
		srcCollection.add(new Person(33, "石榴姐"));
		
		List<Person> destCollection = new ArrayList<Person>();
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("min", 30);
		context.put("max", 70);
		
		service.select(Person.class, srcCollection, destCollection, "WHERE age >= @min AND age <= @max ORDER BY age desc LIMIT 1, 2", context);
		Assert.assertEquals(2, destCollection.size());
		Assert.assertEquals("夏留", destCollection.get(0).getName());
		Assert.assertEquals("黄警", destCollection.get(1).getName());
	}
}
