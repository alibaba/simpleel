/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.simpleEL;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

public class PerformanceAtomicXXXTest extends TestCase {
	final int COUNT = 1000 * 1000;

	public void test_perf() throws Exception {
		perf();
	}

	private void perf() throws Exception {

		for (int i = 0; i < 10; ++i) {
			nano();
		}
		//
		// System.out.println();
		// System.out.println();
		//
		// for (int i = 0; i < 10; ++i) {
		// atomic_long_set();
		// }
	}

	private void nano() throws Exception {
		long startNano = System.nanoTime();
		Object val = new Object();
		for (int i = 0; i < COUNT; ++i) {
			long v = System.currentTimeMillis();
		}

		long nano = System.nanoTime() - startNano;

		System.out.println("nano : " + NumberFormat.getInstance().format(nano));
	}

	private void atomic_int_set() throws Exception {
		long startNano = System.nanoTime();
		AtomicInteger x = new AtomicInteger();
		for (int i = 0; i < COUNT; ++i) {
			x.getAndIncrement();
		}
		long nano = System.nanoTime() - startNano;

		System.out.println("atomic int nano : " + NumberFormat.getInstance().format(nano));
	}

	private void atomic_long_set() throws Exception {
		long startNano = System.nanoTime();
		AtomicLong x = new AtomicLong();
		for (int i = 0; i < COUNT; ++i) {
			x.getAndIncrement();
		}
		long nano = System.nanoTime() - startNano;

		System.out.println("atomic long nano : " + NumberFormat.getInstance().format(nano));
	}

	public static void main(String[] args) throws Exception {
		new PerformanceAtomicXXXTest().perf();
	}
}
