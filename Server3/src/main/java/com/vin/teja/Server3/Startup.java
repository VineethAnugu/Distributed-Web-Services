package com.vin.teja.Server3;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("messageDispatcherServlet")
public class Startup implements DisposableBean {
	
	private final String loadBalancerIP = "localhost";
	private final String loadBalancerUrl = "http://"+ loadBalancerIP +":8082";
	private final String aliveRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:us=\"http://teja.vin.com/service\"><soapenv:Header/><soapenv:Body><us:AliveRequest><us:IPAddress>localhost</us:IPAddress><us:port>8084</us:port><us:serviceNames>AddService,MulService</us:serviceNames></us:AliveRequest></soapenv:Body></soapenv:Envelope>\r\n";
	private final String deadRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:us=\"http://teja.vin.com/service\"><soapenv:Header/><soapenv:Body><us:DeadRequest><us:IPAddress>localhost</us:IPAddress><us:port>8084</us:port></us:DeadRequest></soapenv:Body></soapenv:Envelope>\r\n";
	
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
