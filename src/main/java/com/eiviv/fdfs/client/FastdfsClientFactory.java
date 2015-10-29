package com.eiviv.fdfs.client;

import java.util.List;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.eiviv.fdfs.config.FastdfsClientConfig;

public class FastdfsClientFactory {
	
	private final static String configFile = "fastdfs.properties";
	private static volatile FastdfsClient fastdfsClient;
	private static FastdfsClientConfig config = null;
	
	private FastdfsClientFactory() {
	}
	
	/**
	 * 获取 fastdfs client 客户端
	 * 
	 * @return FastdfsClient实例
	 */
	public static FastdfsClient getFastdfsClient() {
		if (fastdfsClient == null) {
			synchronized (FastdfsClient.class) {
				if (fastdfsClient == null) {
					config = new FastdfsClientConfig(configFile);
					
					int connectTimeout = config.getConnectTimeout();
					int networkTimeout = config.getNetworkTimeout();
					List<String> trackerAddrs = config.getTrackerAddrs();
					
					TrackerClientFactory tcf = new TrackerClientFactory(connectTimeout, networkTimeout);
					StorageClientFactory scf = new StorageClientFactory(connectTimeout, networkTimeout);
					
					GenericKeyedObjectPoolConfig tcpc = config.getTrackerClientPoolConfig();
					GenericKeyedObjectPoolConfig scpc = config.getStorageClientPoolConfig();
					
					GenericKeyedObjectPool<String, TrackerClient> tcp = new GenericKeyedObjectPool<String, TrackerClient>(tcf, tcpc);
					GenericKeyedObjectPool<String, StorageClient> scp = new GenericKeyedObjectPool<String, StorageClient>(scf, scpc);
					
					fastdfsClient = new FastdfsClient(trackerAddrs, tcp, scp);
				}
			}
		}
		
		return fastdfsClient;
	}
	
}
