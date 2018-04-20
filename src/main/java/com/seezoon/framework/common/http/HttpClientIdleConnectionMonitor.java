package com.seezoon.framework.common.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpClientIdleConnectionMonitor extends Thread {

	private static Logger logger = LoggerFactory.getLogger(HttpClientIdleConnectionMonitor.class);

	private static int idleScanTime;
	private static final ArrayList<HttpClientConnectionManager> connectionManagers = new ArrayList<HttpClientConnectionManager>();

	private static HttpClientIdleConnectionMonitor instance;

	private static long idleConnectionTime;

	private volatile boolean shuttingDown;

	private HttpClientIdleConnectionMonitor(HttpClientConfig clientConfig) {
		super("Connection Manager Monitor");
		idleConnectionTime = clientConfig.getIdleTimeToDead();
		idleScanTime = clientConfig.getIdleScanTime();
		setDaemon(true);
	}

	public static synchronized boolean registerConnectionManager(HttpClientConnectionManager connectionManager,
			HttpClientConfig clientConfig) {
		if (instance == null) {
			instance = new HttpClientIdleConnectionMonitor(clientConfig);
			instance.start();
		}
		return connectionManagers.add(connectionManager);
	}

	public static synchronized boolean removeConnectionManager(HttpClientConnectionManager connectionManager) {
		boolean b = connectionManagers.remove(connectionManager);
		if (connectionManagers.isEmpty())
			shutdown();
		return b;
	}

	private void markShuttingDown() {
		shuttingDown = true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			if (shuttingDown) {
				logger.debug("Shutting down Connection Manager Monitor thread.");
				return;
			}

			try {
				Thread.sleep(idleScanTime);
			} catch (InterruptedException e) {
			}

			try {
				List<HttpClientConnectionManager> connectionManagers = null;
				synchronized (HttpClientIdleConnectionMonitor.class) {
					connectionManagers = (List<HttpClientConnectionManager>) HttpClientIdleConnectionMonitor.connectionManagers
							.clone();
				}
				for (HttpClientConnectionManager connectionManager : connectionManagers) {
					try {
						connectionManager.closeExpiredConnections();
						connectionManager.closeIdleConnections(idleConnectionTime, TimeUnit.MILLISECONDS);
					} catch (Exception ex) {
						logger.warn("Unable to close idle connections", ex);
					}
				}
			} catch (Throwable t) {
				logger.debug("Connection Manager Monitor thread: ", t);
			}
		}
	}

	public static synchronized boolean shutdown() {
		if (instance != null) {
			instance.markShuttingDown();
			instance.interrupt();
			connectionManagers.clear();
			instance = null;
			return true;
		}
		return false;
	}

	public static synchronized int size() {
		return connectionManagers.size();
	}

	public static synchronized void setIdleConnectionTime(long idletime) {
		idleConnectionTime = idletime;
	}

}
