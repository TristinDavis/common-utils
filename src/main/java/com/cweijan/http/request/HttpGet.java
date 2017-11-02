package com.cweijan.http.request;

import java.net.HttpURLConnection;

public class HttpGet extends AbstractHttpRequest{

	public HttpGet(String url) {
		this.url=url;
	}

	@Override
	public void buildParam(HttpURLConnection connection) {
		url=url+getParam();
	}

}
