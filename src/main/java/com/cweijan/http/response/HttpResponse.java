package com.cweijan.http.response;

public class HttpResponse {

	private byte[] data;
	
	/**
	 * @param data
	 */
	public HttpResponse(byte[] data) {
		super();
		this.data = data;
	}


	@Override
	public String toString() {
		return new String(data);
	}


	public byte[] getData() {
		return data;
	}


}
