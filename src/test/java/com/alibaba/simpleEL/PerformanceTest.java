package com.alibaba.simpleEL;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import junit.framework.TestCase;

public class PerformanceTest extends TestCase {
	final int COUNT = 1000 * 1000;

	public void test_perf() throws Exception {
		single_map();
	}

	private void single_map() throws Exception {

		Map<String, Object> map;
//		map = Collections.emptyMap();
//		map = new HashMap<String, Object>();
//		map = new TreeMap<String, Object>();
//		map = new Hashtable<String, Object>();
//		map = new ConcurrentHashMap<String, Object>();
//		map = new ConcurrentSkipListMap<String, Object>();
		map = Collections.<String, Object>singletonMap("0", "0");

		for (int i = 0; i < 1; ++i) {
			//map.put(Integer.toString(i), "1000");
		}

		for (int i = 0; i < 10; ++i) {
			map_get(map, "a");
		}
	}

	private void empy_map() throws Exception {

		Map<String, Object> map;
		map = new HashMap<String, Object>();
		// map = new TreeMap<String, Object>();
		// map = Collections.emptyMap();
		// map = new ConcurrentSkipListMap<String, Object>();
		// map = new Hashtable<String, Object>();
		for (int i = 0; i < 10; ++i) {
			map_get(map, "1");
		}
	}

	public void map_get(Map<String, Object> map, Object key) throws Exception {
		long startNano = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			map.get(key);
		}
		long nano = System.nanoTime() - startNano;

		System.out.println("nano : " + NumberFormat.getInstance().format(nano));
	}
}
