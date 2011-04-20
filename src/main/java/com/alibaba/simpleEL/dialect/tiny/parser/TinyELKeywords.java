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
package com.alibaba.simpleEL.dialect.tiny.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shaojin.wensj
 */
public class TinyELKeywords {
    private final Map<String, TinyELToken> keywords;

    public static TinyELKeywords DEFAULT_KEYWORDS;

    static {
        Map<String, TinyELToken> map = new HashMap<String, TinyELToken>();
        map.put("if", TinyELToken.IF);
        map.put("else", TinyELToken.ELSE);
        map.put("return", TinyELToken.RETURN);
        map.put("for", TinyELToken.FOR);
        map.put("while", TinyELToken.WHILE);
        map.put("do", TinyELToken.DO);
        map.put("new", TinyELToken.NEW);
        map.put("null", TinyELToken.NULL);
        map.put("true", TinyELToken.TRUE);
        map.put("false", TinyELToken.FALSE);
        DEFAULT_KEYWORDS = new TinyELKeywords(map);
    }

    public TinyELKeywords(Map<String, TinyELToken> keywords) {
        this.keywords = keywords;
    }

    public TinyELToken getKeyword(String key) {
        return keywords.get(key);
    }

}
