package com.cweijan.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class IOUtils {
	
	private static Logger logger=Log.getLogger();

	public static byte[] readInputStream(InputStream inputStream) throws IOException {

		byte[] data = null;
		byte[] temp = new byte[102400];
		int len = 0;
		logger.debug("pre read");
		while ((len = inputStream.read(temp)) > 0) {
			logger.info("read join");
			System.out.println(new String(temp));
			if (data == null) {
				data = Arrays.copyOf(temp, len);
			} else {
				logger.info("start add");
				data = ArrayUtils.addAll(data, Arrays.copyOf(temp, len));
				logger.info("end add");
			}
		}
		logger.equals("end");
		return data;
	}

}
