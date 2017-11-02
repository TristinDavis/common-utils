package com.cweijan.test;

import java.io.IOException;

import com.cweijan.http.core.HttpClient;
import com.cweijan.http.request.HttpPost;
import com.cweijan.http.request.HttpRequest;
import com.cweijan.http.response.HttpResponse;
import com.cweijan.util.DateUtils;
import com.cweijan.util.TestUtils;

public class HttpTest {
	
	public static void main(String[] args) throws IOException {
		
		TestUtils.begin();
		HttpRequest post = new HttpPost("http://www.jianshu.com/notes/0842b888d94a/mark_viewed.json");
//		post.addParam("uuid","3a1c5f72-7462-49a7-83bc-16ad03057c97");
//		post.addParam("referrer","https://www.google.com/");
		HttpResponse response = HttpClient.request(post);
		System.out.println(response);

		TestUtils.end();
		System.out.println(DateUtils.getIntervalDay(-1));
		
		
	}

}
