package com.cweijan.http.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import com.cweijan.http.request.HttpGet;
import com.cweijan.http.request.HttpRequest;
import com.cweijan.http.response.HttpResponse;
import com.cweijan.util.Log;

public class HttpClient {

	public static HttpResponse request(HttpRequest request) {

		byte[] data = null;
		try {
			URL url = new URL(request.getUrl());
			
			HttpURLConnection connection = request.open(url);;
			request.buildHeader(connection);
			request.buildParam(connection);
			request.buildTimeout(connection);
			data = readResult(connection);
		} catch (Exception e) {
			Log.getLogger().error(e.getMessage(),e);
		}

		return new HttpResponse(data);
	}

	public static HttpResponse get(String host) throws IOException {
		return request(new HttpGet(host));
	}
	
	private static byte[] readResult(HttpURLConnection connection) throws IOException {
		InputStream inputStream = connection.getInputStream();
		byte[] data = null;
		byte[] temp = new byte[102400];
		int len = 0;
		while ((len = inputStream.read(temp)) != -1) {
			if (data == null) {
				data = Arrays.copyOf(temp, len);
			} else {
				data = ArrayUtils.addAll(data, Arrays.copyOf(temp, len));
			}
		}
		return data;
	}
}
