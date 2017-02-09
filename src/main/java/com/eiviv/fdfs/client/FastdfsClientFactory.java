package com.eiviv.fdfs.client;

import java.util.List;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import com.eiviv.fdfs.config.FastdfsClientConfig;

public class FastdfsClientFactory {
	
	private final static String DEFAULT_CONFIG_FILE = "fastdfs.properties";
	private static volatile FastdfsClient fastdfsClient;

	private FastdfsClientFactory() {
	}

	/**
	 * 获取 fastdfs client 客户端
	 *
	 * @param config
	 * @return
     */
	public static FastdfsClient getFastdfsClient(FastdfsClientConfig config) {
		if (fastdfsClient == null) {
			synchronized (FastdfsClient.class) {
				if (fastdfsClient == null) {
					int connectTimeout = config.getConnectTimeout();
					int networkTimeout = config.getNetworkTimeout();
					List<String> trackerAddrs = config.getTrackerAddrs();

					TrackerClientFactory tcf = new TrackerClientFactory(connectTimeout, networkTimeout);
					StorageClientFactory scf = new StorageClientFactory(connectTimeout, networkTimeout);

					GenericKeyedObjectPoolConfig tcpc = config.getTrackerPoolConfig();
					GenericKeyedObjectPoolConfig scpc = config.getStoragePoolConfig();

					GenericKeyedObjectPool<String, TrackerClient> tcp = new GenericKeyedObjectPool<String, TrackerClient>(tcf, tcpc);
					GenericKeyedObjectPool<String, StorageClient> scp = new GenericKeyedObjectPool<String, StorageClient>(scf, scpc);

					fastdfsClient = new FastdfsClient(trackerAddrs, tcp, scp);
				}
			}
		}

		return fastdfsClient;
	}

	/**
	 * 获取 fastdfs client 客户端
	 *
	 * @param configFile
	 * @return
     */
	public static FastdfsClient getFastdfsClient(String configFile) {
		return FastdfsClientFactory.getFastdfsClient(new FastdfsClientConfig(configFile));
	}

	/**
	 * 获取 fastdfs client 客户端
	 *
	 * @return
     */
	public static FastdfsClient getFastdfsClient() {
		return FastdfsClientFactory.getFastdfsClient(DEFAULT_CONFIG_FILE);
	}
}
