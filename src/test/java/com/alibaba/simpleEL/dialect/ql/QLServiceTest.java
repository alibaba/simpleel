package com.alibaba.simpleEL.dialect.ql;

import java.util.ArrayList;
import java.util.List;

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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public void test_0 () throws Exception {
		QLService service = new QLService();
		
		List<Person> srcCollection = new ArrayList<Person>();
		srcCollection.add(new Person(18, "吴能"));
		srcCollection.add(new Person(27, "刘芒"));
		srcCollection.add(new Person(40, "黄警"));
		srcCollection.add(new Person(50, "夏留"));
		
		List<Person> destCollection = new ArrayList<Person>();
		
		service.select(Person.class, srcCollection, destCollection, "WHERE age > @age ORDER BY age desc");
		Assert.assertEquals(2, destCollection.size());
		Assert.assertEquals("夏留", destCollection.get(0).getName());
		Assert.assertEquals("黄警", destCollection.get(1).getName());
	}
}
