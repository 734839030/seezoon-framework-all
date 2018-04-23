package com.seezoon.framework.common.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class DefaultHttpPoolClientTest {

	@Test
	public void t1() throws ClientProtocolException, IOException {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", "APPID");
		params.put("secret", "APPSECRET");
		for (int i = 0;i<100;i ++ ) {
			String doGet = HttpRequestUtils.doPost("https://api.weixin.qq.com/cgi-bin/token", params);
			String login = HttpRequestUtils.doPost("http://admin-b.cnmmt.com/sys/user/login", params);

			System.out.println(doGet);
			System.out.println(login);

			
		}
		System.in.read();
		}
}
