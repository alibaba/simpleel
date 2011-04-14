package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    }
}
