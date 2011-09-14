package com.alibaba.simpleEL;

import java.util.*;
import static java.lang.Math.*;
import com.alibaba.simpleEL.Expr;
import static com.alibaba.simpleEL.TypeUtils.*;

public class GenClass_10000 implements Expr {

	public Object eval(Map<String, Object> ctx) {
		List<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person> _src_ = (List<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person>) ctx.get("_src_");
		List<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person> _dest_ = (List<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person>) ctx.get("_dest_");
		final int offset = 1;
		final int rowCount = 2;

		int rowIndex = 0;
		for (com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person item : _src_) {

			if(item.getAge() > _int(ctx.get("age"))) {
				_dest_.add(item);
			}

			{
				Comparator<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person> comparator = new Comparator<com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person>() {
					public int compare(com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person a, com.alibaba.simpleEL.dialect.ql.bvt.QLServiceTest.Person b) {
						if (a.getAge() > b.getAge()) {
							return -1;
						}
						if (a.getAge() < b.getAge()) {
							return 1;
						}
						return 0;
					}
				};
				Collections.sort(_dest_, comparator);
			}

			for (int i = 0; i < offset && i < _dest_.size(); ++i) {
				_dest_.remove(0);
			}
			while(_dest_.size() > rowCount) {
				_dest_.remove(_dest_.size() - 1);
			}

		}

		return null;
	}

}

