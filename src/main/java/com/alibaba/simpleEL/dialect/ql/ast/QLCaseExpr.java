package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLCaseExpr extends QLExpr implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Item> items = new ArrayList<Item>();
	private QLExpr valueExpr;
	private QLExpr elseExpr;

	public QLCaseExpr() {

	}

	public QLExpr getValueExpr() {
		return this.valueExpr;
	}

	public void setValueExpr(QLExpr valueExpr) {
		this.valueExpr = valueExpr;
	}

	public QLExpr getElseExpr() {
		return this.elseExpr;
	}

	public void setElseExpr(QLExpr elseExpr) {
		this.elseExpr = elseExpr;
	}

	public List<Item> getItems() {
		return this.items;
	}
	
    protected void accept0(QLAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.valueExpr);
            acceptChild(visitor, this.items);
            acceptChild(visitor, this.elseExpr);
        }
        visitor.endVisit(this);
    }

	public void output(StringBuffer buf) {
		buf.append("CASE ");
		if (this.valueExpr != null) {
			this.valueExpr.output(buf);
			buf.append(" ");
		}

		int i = 0;
		for (int size = this.items.size(); i < size; ++i) {
			if (i != 0) {
				buf.append(" ");
			}
			((Item) this.items.get(i)).output(buf);
		}

		if (this.elseExpr != null) {
			buf.append(" ELSE ");
			this.elseExpr.output(buf);
		}

		buf.append(" END");
	}

	public static class Item extends QLAstNode implements Serializable {
		private static final long serialVersionUID = 1L;
		private QLExpr conditionExpr;
		private QLExpr valueExpr;

		public Item() {

		}

		public Item(QLExpr conditionExpr, QLExpr valueExpr) {

			this.conditionExpr = conditionExpr;
			this.valueExpr = valueExpr;
		}

		public QLExpr getConditionExpr() {
			return this.conditionExpr;
		}

		public void setConditionExpr(QLExpr conditionExpr) {
			this.conditionExpr = conditionExpr;
		}

		public QLExpr getValueExpr() {
			return this.valueExpr;
		}

		public void setValueExpr(QLExpr valueExpr) {
			this.valueExpr = valueExpr;
		}

		@Override
		protected void accept0(QLAstVisitor visitor) {
			if (visitor.visit(this)) {
				acceptChild(visitor, this.conditionExpr);
				acceptChild(visitor, this.valueExpr);
			}
			visitor.endVisit(this);
		}

        public void output(StringBuffer buf) {
            buf.append("WHEN ");
            this.conditionExpr.output(buf);
            buf.append(" THEN ");
            this.valueExpr.output(buf);
        }
	}
}
