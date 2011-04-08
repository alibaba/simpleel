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
package com.alibaba.simpleEL.bvt;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import com.alibaba.simpleEL.ExpressEvalService;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;
import com.alibaba.simpleEL.preprocess.VariantResolver;

public class DefaultExpressEvalServiceTest extends TestCase {
    public void test_eval() throws Exception {
        ExpressEvalService service = new DefaultExpressEvalService();

        //ManagementFactory.getPlatformMBeanServer().registerMBean(service, new ObjectName("com.alibaba.simpleEL:type=ExpressEvalService"));

        //service.setProfileEnable(true);

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("Count", 4);

        //Assert.assertEquals(3, service.eval(ctx, "3"));
        //Assert.assertEquals(true, service.eval(ctx, "_int(@Count) > 3"));

        perf(service, ctx);
        for (int i = 0; i < 10; ++i) {
            perf(service, ctx);
        	
        }
    }
    
    private void perf_nano() {
    	//final Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
    	//final Map<String, Object> map = new TreeMap<String, Object>();
    	//final Map<String, Object> map = new HashMap<String, Object>();
    	final Map<String, Object> map = new ConcurrentHashMap<String, Object>();
    	map.put("1", "1");
    	//final Map<String, Object> map = Collections.<String, Object>singletonMap("1", "1");
    	
    	final AtomicInteger v = new AtomicInteger();
        final int COUNT = 1000 * 1000 * 10;
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {
            //v.incrementAndGet();
        	map.get("1");
        }
        long millis = System.currentTimeMillis() - startMillis;
        System.out.println("millis : " + millis);
    }

    public void f_test_hmk() throws Exception {
        ExpressEvalService service = new DefaultExpressEvalService();
        TemplatePreProcessor preprocessor = (TemplatePreProcessor) service.getPreprocessor();
        preprocessor.setVariantResolver(new VariantResolver() {

            @Override
            public String resolve(String variant) {
                return "_int(ctx.get(\"" + variant + "\"))";
            }
        });

        //service.setProfileEnable(true);

        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("request_num", 20000);
        vars.put("age", 200);
        vars.put("__lastloginid__", 100);
        vars.put("cookie2", 23);
        String expStr = "(@request_num >= 800) && (@request_num * 1.0 / @age >= 30) && ( (@__lastloginid__ * 1.0 /@request_num < 0.008) || (@__lastloginid__ * 1.0 / @request_num > 0.04) )";
        Boolean result = (Boolean) service.eval(vars, expStr);
        System.out.println("result:" + result);

        long startMillis = System.currentTimeMillis();

        for (int i = 0; i < 1000 * 1000; ++i) {
            vars.put("request_num", i);
            vars.put("age", i);
            vars.put("__lastloginid__", i);
            service.eval(vars, expStr);
        	//perf_nano();
        }

        long millis = System.currentTimeMillis() - startMillis;
        System.out.println("millis : " + millis);
    }

    private void perf(ExpressEvalService service, Map<String, Object> ctx) {
        final int COUNT = 1000 * 1000;
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < COUNT; ++i) {
            service.eval(ctx, "_int(@Count) != 3");
        }
        long millis = System.currentTimeMillis() - startMillis;
        System.out.println("millis : " + millis);
    }

}
