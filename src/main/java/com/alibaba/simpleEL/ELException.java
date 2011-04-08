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
package com.alibaba.simpleEL;

/**
 * @author shaojin.wensj
 *
 */
public class ELException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ELException() {
	}

	public ELException(String message) {
		super(message);
	}

	public ELException(String message, Throwable cause) {
		super(message, cause);
	}
}
