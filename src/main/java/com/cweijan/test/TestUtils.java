package com.cweijan.test;

import com.cweijan.util.Log;

public class TestUtils {
	
	private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
	
	public static void loopTest(TestUnit testUnit,int count) {
		for(int i=0;i<count;i++) {
			testUnit.test();
		}
	}
	
	public static void begin() {
		threadLocal.set(System.currentTimeMillis());
	}

	public static void end() {
		
		Log.getLogger().debug("time:"+(System.currentTimeMillis()-threadLocal.get())+" millSecond");

	}

}
