package com.cweijan.http.request;

import java.net.HttpURLConnection;

public interface HttpRequest {

	public void addHeader(String headerName,String headerValue);
	
	public void addParam(String paramName,String paramValue);

	public void buildParam(HttpURLConnection connection);
	
	public String getUrl();

	public void buildHeader(HttpURLConnection connection);
	
}
