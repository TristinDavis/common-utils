package com.cweijan.http.request;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.cweijan.util.Log;

public class HttpPost extends AbstractHttpRequest{

	public HttpPost(String url) {
		this.url=url;
	}

	@Override
	public void buildParam(HttpURLConnection connection) {
		connection.setDoOutput(true);
		try {
			connection.getOutputStream().write(getParam().getBytes());
		} catch (IOException e) {
			Log.getLogger().error(e.getMessage(),e);
		}
	}

}
