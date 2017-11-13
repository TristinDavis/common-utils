package com.cweijan.http.request;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cweijan.http.DefaultConfig;


@SuppressWarnings("unused")
public abstract class AbstractHttpRequest implements HttpRequest {

	private String param = "";
	protected String url;
	protected Map<String, String> header;
	private Proxy proxy;
	private int timeout=DefaultConfig.connectTimeout;
	private int readTimeout=DefaultConfig.readTimeout;

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

	@Override
	public void setProxy(String host, int port) {
		proxy=new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
	}

	public Proxy getProxy() {
		return proxy;
	}

	@Override
	public HttpURLConnection open(URL url) throws IOException{
		URLConnection connection=null;
		if (proxy != null) {
			connection=url.openConnection(proxy);
		}else {
			connection=url.openConnection();
		}
		
		return (HttpURLConnection) connection;
	}

	@Override
	public void buildTimeout(HttpURLConnection connection) {
		connection.setConnectTimeout(timeout);
		connection.setReadTimeout(readTimeout);
	}
	
	@Override
	public void setTimeout(int timeout) {
		this.timeout=timeout;
	}
	
	@Override
	public void setReadTimeout(int timeout) {
		this.readTimeout=timeout;
	}
	
}
