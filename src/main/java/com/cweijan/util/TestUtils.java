package com.cweijan.util;

public class TestUtils {
	
	private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
	
	
	public static void begin() {
		threadLocal.set(System.currentTimeMillis());
	}

	public static void end() {
		
		Log.getLogger().debug("time:"+(System.currentTimeMillis()-threadLocal.get())+" millSecond");

	}

}
