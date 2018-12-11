package com.vin.teja.Server2;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class GetIP {
	
	public String get_ip() {
		int i = -1;
		String [] IP = new String[2];
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                    	i++;
                        IP[i]=inetAddress.getHostAddress().toString();
                        System.out.println(IP[i]);

                    }
                }
            }
        } catch (SocketException ex) {
        	ex.printStackTrace();
        }
        
        return IP[i];
	}



	public static void main(String[] args) throws UnknownHostException {
			
}
}