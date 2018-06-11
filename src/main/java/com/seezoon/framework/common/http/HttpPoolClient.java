package com.seezoon.framework.common.http;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seezoon.framework.common.context.exception.ServiceException;

/**
 * 带连接池的http client 请勿频繁实例化
 * 
 * @author hdf 2017年10月21日 doc @see
 *         http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/pdf/httpclient-tutorial.pdf
 *         http://hc.apache.org/httpcomponents-client-4.5.x/examples.html
 */
public class HttpPoolClient {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpPoolClient.class);

	private HttpClientConfig httpClientConfig;
	private HttpClientConnectionManager httpClientConnectionManager;
	private CloseableHttpClient httpClient;

	public HttpPoolClient() {
		this(new HttpClientConfig());
	}

	public HttpPoolClient(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
		httpClientConnectionManager = createHttpClientConnectionManager();
		httpClient = createHttpClient(httpClientConnectionManager);
	}
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	private CloseableHttpClient createHttpClient(HttpClientConnectionManager connectionManager) {
		//HttpHost proxy = new HttpHost("127.0.0.1",8889);
		RequestConfig requestConfig = RequestConfig.custom()
				//.setProxy(proxy)
				.setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout())// 获取连接等待时间
				.setConnectTimeout(httpClientConfig.getConnectTimeout())// 连接超时
				.setSocketTimeout(httpClientConfig.getSocketTimeout())// 获取数据超时
				.build();
		httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig)
				.setUserAgent(httpClientConfig.getUserAgent())
				.disableContentCompression().disableAutomaticRetries()
				.setConnectionTimeToLive(httpClientConfig.getConnTimeToLive(), TimeUnit.MILLISECONDS)// 连接最大存活时间
				.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientConfig.getRetyTimes(), true))// 重试次数
				.build();
		return httpClient;
	}

	public  HttpClientConnectionManager createHttpClientConnectionManager() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return false;
				}
			}).build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
				.build();
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		// 最大连接数
		poolingHttpClientConnectionManager.setMaxTotal(httpClientConfig.getMaxTotal());
		// 单个站点最大连接数
		poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientConfig.getMaxPerRoute());
		// 长连接
		poolingHttpClientConnectionManager.setDefaultSocketConfig(
				SocketConfig.custom().setSoTimeout(httpClientConfig.getSocketTimeout()).setSoKeepAlive(true).build());
		// 连接不活跃多久检查毫秒 并不是100 % 可信
		poolingHttpClientConnectionManager.setValidateAfterInactivity(httpClientConfig.getValidateAfterInactivity());
		// 空闲扫描线程
		HttpClientIdleConnectionMonitor.registerConnectionManager(poolingHttpClientConnectionManager, httpClientConfig);
		return poolingHttpClientConnectionManager;
	}

	public void shutdown() {
		HttpClientIdleConnectionMonitor.removeConnectionManager(this.httpClientConnectionManager);
		this.httpClientConnectionManager.shutdown();
	}

	
	public CloseableHttpResponse execute(HttpRequestBase request) {
		try {
			return httpClient.execute(request);
		} catch (IOException e) {
			request.abort();
			throw new ServiceException(e);
		}
	}

	public CloseableHttpResponse execute(HttpRequestBase request,HttpClientContext httpClientContext) {
		try {
			return httpClient.execute(request,httpClientContext);
		} catch (IOException e) {
			request.abort();
			throw new ServiceException(e);
		}
	}
}
