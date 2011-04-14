/**
 * Project: fastjson
 * 
 * File Created at 2010-12-2
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.simpleEL.dialect.ql.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaojin.wensj
 */
public class Keywords {
    private final Map<String, QLToken> keywords;

    public static Keywords DEFAULT_KEYWORDS;

    static {
        Map<String, QLToken> map = new HashMap<String, QLToken>();
        map.put("EXISTS", QLToken.EXISTS);
        map.put("THEN", QLToken.THEN);
        map.put("AS", QLToken.AS);
        map.put("GROUP", QLToken.GROUP);
        map.put("BY", QLToken.BY);
        map.put("HAVING", QLToken.HAVING);
        map.put("ORDER", QLToken.ORDER);
        map.put("VALUES", QLToken.VALUES);
        map.put("NULL", QLToken.NULL);
        map.put("IS", QLToken.IS);
        map.put("NOT", QLToken.NOT);
        map.put("SELECT", QLToken.SELECT);
        map.put("INSERT", QLToken.INSERT);
        map.put("FROM", QLToken.FROM);
        map.put("WHERE", QLToken.WHERE);
        map.put("AND", QLToken.AND);
        map.put("OR", QLToken.OR);
        map.put("XOR", QLToken.XOR);
        map.put("DISTINCT", QLToken.DISTINCT);
        map.put("ALL", QLToken.ALL);
        map.put("NEW", QLToken.NEW);
        map.put("CASE", QLToken.CASE);
        map.put("WHEN", QLToken.WHEN);
        map.put("END", QLToken.END);
        map.put("WHEN", QLToken.WHEN);
        map.put("ELSE", QLToken.ELSE);
        map.put("EXISTS", QLToken.EXISTS);
        map.put("IN", QLToken.IN);
        map.put("ASC", QLToken.ASC);
        map.put("DESC", QLToken.DESC);
        map.put("LIKE", QLToken.LIKE);
        map.put("ESCAPE", QLToken.ESCAPE);
        map.put("BETWEEN", QLToken.BETWEEN);
        map.put("INTERVAL", QLToken.INTERVAL);
        DEFAULT_KEYWORDS = new Keywords(map);
    }

    public Keywords(Map<String, QLToken> keywords) {
        this.keywords = keywords;
    }

    public QLToken getKeyword(String key) {
        key = key.toUpperCase();
        return keywords.get(key);
    }

}
