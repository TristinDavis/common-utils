package com.cweijan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	public static Logger getLogger() {
		StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];

		String invokeClassName = stackTrace.getClassName();

		return LoggerFactory.getLogger(invokeClassName);
	}

}