package com.cweijan.http.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpRequest {

	public void addHeader(String headerName, String headerValue);

	public void addParam(String paramName, String paramValue);

	public String getUrl();

	public void setProxy(String host, int port);

	public void buildParam(HttpURLConnection connection);

	public void buildHeader(HttpURLConnection connection);

	public void buildTimeout(HttpURLConnection connection);
	public HttpURLConnection open(URL url) throws IOException;

	public void setTimeout(int timeout);
	
	public void setReadTimeout(int timeout);

	
}
