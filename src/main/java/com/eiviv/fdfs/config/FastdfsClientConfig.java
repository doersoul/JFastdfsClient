package com.eiviv.fdfs.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastdfsClientConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(FastdfsClientConfig.class);
	
	public static final int DEFAULT_CONNECT_TIMEOUT = 5; // second
	public static final int DEFAULT_NETWORK_TIMEOUT = 30; // second
	
	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT * 1000;
	private int networkTimeout = DEFAULT_NETWORK_TIMEOUT * 1000;
	private List<String> trackerAddrs = new ArrayList<String>();
	
	/**
	 * 实例化
	 */
	public FastdfsClientConfig() {
	}
	
	/**
	 * 实例化
	 * 
	 * @param configFile 配置文件名
	 * @throws ConfigurationException
	 */
	public FastdfsClientConfig(String configFile) {
		try {
			Configuration config = new PropertiesConfiguration(configFile);
			List<Object> trackerServers = config.getList("tracker_server");
			
			for (Object trackerServer : trackerServers) {
				trackerAddrs.add((String) trackerServer);
			}
			
			connectTimeout = config.getInt("connect_timeout", DEFAULT_CONNECT_TIMEOUT) * 1000;
			networkTimeout = config.getInt("network_timeout", DEFAULT_NETWORK_TIMEOUT) * 1000;
		} catch (ConfigurationException e) {
			logger.error("init fastdfs client config error", e);
		}
	}
	
	public GenericKeyedObjectPoolConfig getTrackerClientPoolConfig() {
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		
		return poolConfig;
	}
	
	public GenericKeyedObjectPoolConfig getStorageClientPoolConfig() {
		GenericKeyedObjectPoolConfig poolConfig = new GenericKeyedObjectPoolConfig();
		
		return poolConfig;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	public int getNetworkTimeout() {
		return networkTimeout;
	}
	
	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}
	
	public List<String> getTrackerAddrs() {
		return trackerAddrs;
	}
	
	public void setTrackerAddrs(List<String> trackerAddrs) {
		this.trackerAddrs = trackerAddrs;
	}
	
}
