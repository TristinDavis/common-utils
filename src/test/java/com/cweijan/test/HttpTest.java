package com.cweijan.test;

import java.io.IOException;

import com.cweijan.http.core.HttpClient;
import com.cweijan.http.request.HttpGet;
import com.cweijan.http.request.HttpRequest;
import com.cweijan.http.response.HttpResponse;

public class HttpTest {

	public static void main(String[] args) throws IOException {

		TestUtils.begin();
		TestUtils.loopTest(() -> {
			HttpRequest post = new HttpGet("https://ip54264223.ahcdn.com/key=3SI90nK3soUSm-yVV1587w,s=,end=1509476745/state=LU2Y/reftag=56109644/media=hlsA/ssd3/177/9/56385209.mp4/seg-1-v1-a1.ts");
			post.setProxy("127.0.0.1", 54317);
			HttpResponse response = HttpClient.request(post);
			System.out.println(response);
		}, 1);

		TestUtils.end();

	}

}
