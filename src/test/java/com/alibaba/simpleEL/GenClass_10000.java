package com.alibaba.simpleEL;

import static com.alibaba.simpleEL.TypeUtils._int;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person;

public class GenClass_10000 implements Expr {

	public Object eval(Map<String, Object> ctx) {
		List<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person> _src_ = (List<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person>) ctx.get("_src_");
		List<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person> _dest_ = (List<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person>) ctx.get("_dest_");
		for (com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person item : _src_) {

			if(item.getAge() > _int(ctx.get("age"))) {
				_dest_.add(item);
			}

		}
		
		Comparator<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person> comparator = new Comparator<com.alibaba.simpleEL.dialect.ql.QLServiceTest.Person>() {
			@Override
			public int compare(Person a, Person b) {
				if (a.getAge() > b.getAge()) {
					return 1;
				}
				
				if (b.getAge() > a.getAge()) {
					return -1;
				}
				return 0;
			}
		};

		return null;
	}

}