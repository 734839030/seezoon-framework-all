package com.seezoon.framework.common.http;

/**
 * Http client 的默认配置项
 * 
 * @author hdf 2018年4月20日
 */
public class HttpClientConfig {

	// 连接超时 ms
	private int connectTimeout = 6 * 1000;
	// 获取数据超时 ms
	private int socketTimeout = 10 * 1000;
	// 获取连接超时ms
	private int connectionRequestTimeout = 10 * 1000;
	// 最大线程数
	private int maxTotal = 100;
	// 站点最大连接数
	private int maxPerRoute = maxTotal;
	// 不活跃多久检查ms
	private int validateAfterInactivity = 2 * 1000;
	// 重试次数 0 不重试
	private int retyTimes = 3;
	// 空闲时间多久销毁ms
	private int idleTimeToDead = 60 * 1000;
	// 连接最多存活多久ms
	private int connTimeToLive = 60 * 1000;
	//清理空闲线程
	private int idleScanTime = 5 * 1000;
	//默认请求头
	private String userAgent  = "seezoon-framework "  + "(" + 
            System.getProperty("os.name") + "/" + System.getProperty("os.version") + "/" +
            System.getProperty("os.arch") + ";" + System.getProperty("java.version") + ")";

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxPerRoute() {
		return maxPerRoute;
	}

	public void setMaxPerRoute(int maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
	}

	public int getValidateAfterInactivity() {
		return validateAfterInactivity;
	}

	public void setValidateAfterInactivity(int validateAfterInactivity) {
		this.validateAfterInactivity = validateAfterInactivity;
	}

	public int getRetyTimes() {
		return retyTimes;
	}

	public void setRetyTimes(int retyTimes) {
		this.retyTimes = retyTimes;
	}

	public int getIdleTimeToDead() {
		return idleTimeToDead;
	}

	public void setIdleTimeToDead(int idleTimeToDead) {
		this.idleTimeToDead = idleTimeToDead;
	}

	public int getConnTimeToLive() {
		return connTimeToLive;
	}

	public void setConnTimeToLive(int connTimeToLive) {
		this.connTimeToLive = connTimeToLive;
	}

	public int getIdleScanTime() {
		return idleScanTime;
	}

	public void setIdleScanTime(int idleScanTime) {
		this.idleScanTime = idleScanTime;
	}

}
