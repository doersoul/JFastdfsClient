package com.eiviv.fdfs.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import com.eiviv.fdfs.cmd.CloseCmd;
import com.eiviv.fdfs.cmd.Cmd;
import com.eiviv.fdfs.cmd.QueryDownloadCmd;
import com.eiviv.fdfs.cmd.QueryGroupInfoCmd;
import com.eiviv.fdfs.cmd.QueryStorageInfoCmd;
import com.eiviv.fdfs.cmd.QueryUpdateCmd;
import com.eiviv.fdfs.cmd.QueryUploadCmd;
import com.eiviv.fdfs.config.FastdfsClientConfig;
import com.eiviv.fdfs.model.GroupInfo;
import com.eiviv.fdfs.model.Result;
import com.eiviv.fdfs.model.StorageInfo;
import com.eiviv.fdfs.model.UploadStorage;

public class TrackerClient extends AbstractClient {
	
	private Integer connectTimeout = FastdfsClientConfig.DEFAULT_CONNECT_TIMEOUT * 1000;
	private Integer networkTimeout = FastdfsClientConfig.DEFAULT_NETWORK_TIMEOUT * 1000;
	private Socket socket;
	private String host;
	private Integer port;
	
	/**
	 * 实例化
	 * 
	 * @param address "host:port"
	 */
	public TrackerClient(String address) {
		String[] hostport = address.split(":");
		
		this.host = hostport[0];
		this.port = Integer.valueOf(hostport[1]);
	}
	
	/**
	 * 实例化
	 * 
	 * @param address "host:port"
	 * @param connectTimeout 连接超时时间(秒)
	 * @param networkTimeout 传输超时时间(秒)
	 */
	public TrackerClient(String address, Integer connectTimeout, Integer networkTimeout) {
		this(address);
		
		this.connectTimeout = connectTimeout;
		this.networkTimeout = networkTimeout;
	}
	
	/**
	 * 获取更新 storage
	 * 
	 * @return
	 * @throws IOException
	 */
	public Result<UploadStorage> getUploadStorage() throws IOException {
		Cmd<UploadStorage> command = new QueryUploadCmd();
		
		return command.exec(getSocket());
	}
	
	/**
	 * 获取更新 storage 地址
	 * 
	 * @param group 组名
	 * @param fileName 文件名
	 * @return 更新 storage 地址
	 * @throws IOException
	 */
	public Result<String> getUpdateStorageAddr(String group, String fileName) throws IOException {
		Cmd<String> cmd = new QueryUpdateCmd(group, fileName);
		
		return cmd.exec(getSocket());
	}
	
	/**
	 * 获取下载 storage 地址
	 * 
	 * @param group 组名
	 * @param fileName 文件名
	 * @return 下载 storage 地址
	 * @throws IOException
	 */
	public Result<String> getDownloadStorageAddr(String group, String fileName) throws IOException {
		Cmd<String> cmd = new QueryDownloadCmd(group, fileName);
		
		return cmd.exec(getSocket());
	}
	
	/**
	 * 获取组信息
	 * 
	 * @return 组信息
	 * @throws IOException
	 */
	public Result<List<GroupInfo>> getGroupInfos() throws IOException {
		Cmd<List<GroupInfo>> cmd = new QueryGroupInfoCmd();
		
		return cmd.exec(getSocket());
	}
	
	/**
	 * 获取 StorageInfos
	 * 
	 * @param group 组名
	 * @return StorageInfo 集合
	 * @throws IOException
	 */
	public Result<List<StorageInfo>> getStorageInfos(String group) throws IOException {
		Cmd<List<StorageInfo>> cmd = new QueryStorageInfoCmd(group);
		
		return cmd.exec(getSocket());
	}
	
	/**
	 * 关闭socket连接
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		Socket socket = getSocket();
		Cmd<Boolean> cmd = new CloseCmd();
		cmd.exec(socket);
		socket.close();
		socket = null;
	}
	
	/**
	 * 获取socket
	 * 
	 * @return
	 * @throws IOException
	 */
	private Socket getSocket() throws IOException {
		
		if (socket == null) {
			socket = new Socket();
			socket.setSoTimeout(networkTimeout);
			socket.connect(new InetSocketAddress(host, port), connectTimeout);
		}
		
		return socket;
	}
}
