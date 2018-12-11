package com.vin.teja.Server2;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.vin.teja.Server2.GetIP;

@Component
@DependsOn("messageDispatcherServlet")
public class Startup implements DisposableBean {
	
	String IP = new GetIP().get_ip();
	private final String loadBalancerIP = "10.200.152.62";
	private final String loadBalancerUrl = "http://"+ loadBalancerIP +":8082";
	private final String aliveRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:us=\"http://teja.vin.com/service\"><soapenv:Header/><soapenv:Body><us:AliveRequest><us:IPAddress>"+IP+"</us:IPAddress><us:port>8083</us:port><us:serviceNames>MulService,DivService</us:serviceNames></us:AliveRequest></soapenv:Body></soapenv:Envelope>\r\n";
	private final String deadRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:us=\"http://teja.vin.com/service\"><soapenv:Header/><soapenv:Body><us:DeadRequest><us:IPAddress>"+IP+"</us:IPAddress><us:port>8083</us:port></us:DeadRequest></soapenv:Body></soapenv:Envelope>\r\n";
	
	public Startup() {}
	
	@PostConstruct
	public void init() {
		HTTPConnection http = new HTTPConnection(loadBalancerUrl, aliveRequest, 3000L);
		Thread t = new Thread(http);
		t.start();
	}

	@PreDestroy
	@Override
	public void destroy() throws Exception {
		//TODO check if server IP is not loadBalancerIP
		HTTPConnection http = new HTTPConnection(loadBalancerUrl, deadRequest);
		Thread t = new Thread(http);
		t.start();
		t.join();
	}
	
}
