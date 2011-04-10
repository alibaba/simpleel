package com.alibaba.simpleEL.compile;

import java.util.List;

public interface JdkCompilerMBean {
	List<String> getOptions();
	
	long getCompileCount();
	
	long getCompileTimeNano();
}
