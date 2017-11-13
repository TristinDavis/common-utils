package com.cweijan.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class IOUtils {
	
	public static byte[] readInputStream(InputStream inputStream) throws IOException {

		byte[] data = null;
		byte[] temp = new byte[102400];
		int len = 0;
		while ((len = inputStream.read(temp)) > 0) {
			System.out.println(new String(temp));
			if (data == null) {
				data = Arrays.copyOf(temp, len);
			} else {
				data = ArrayUtils.addAll(data, Arrays.copyOf(temp, len));
			}
		}
		return data;
	}

}
