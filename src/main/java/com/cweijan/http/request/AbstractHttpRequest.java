package com.cweijan.http.request;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class AbstractHttpRequest implements HttpRequest {

	private String param = "";
	protected String url;
	protected Map<String, String> header;

	public AbstractHttpRequest() {
		header = new HashMap<>();
	}

	@Override
	public void addHeader(String name, String value) {
		if (header == null) {
			header = new HashMap<>(10);
		}
		header.put(name, value);
	}

	@Override
	public void addParam(String paramName, String paramValue) {
		param = paramName + "=" + paramValue + "&";
	}

	protected String getParam() {
		int index = param.lastIndexOf("&");
		if(index==-1) {
			return param;
		}
		return param.substring(0,index);
	}
	
	
	@Override
	public void buildHeader(HttpURLConnection connection) {
		if (header == null) {
			return;
		}
		Set<Entry<String, String>> entries = header.entrySet();
		for (Entry<String, String> entry : entries) {
			connection.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}

	public String getUrl() {
		return url;
	}

}
