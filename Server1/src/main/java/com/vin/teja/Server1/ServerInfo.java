package com.vin.teja.Server1;

public class ServerInfo {

	public String ipAddress;
	private int port;
	private String[] serviceNames;
	private int load;
	
	public int getLoad() {
		return load;
	}
	public void setLoad(int load) {
		this.load = load;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String[] getServiceNames() {
		return serviceNames;
	}
	public void setServiceNames(String[] serviceNames) {
		this.serviceNames = serviceNames;
	}
	
	public String getKey() {
		return this.ipAddress +":"+ this.port;
	}

}