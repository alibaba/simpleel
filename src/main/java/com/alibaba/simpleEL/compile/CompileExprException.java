/**
 * Project: simple-el
 * 
 * File Created at 2010-12-2
 * $Id$
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
package com.alibaba.simpleEL.compile;

/**
 * @author shaojin.wensj
 *
 */
@SuppressWarnings("serial")
public class CompileExprException extends RuntimeException {
	public CompileExprException(String message) {
		super(message);
	}
	
    public CompileExprException(String message, Throwable cause) {
        super(message, cause);
    }
}
