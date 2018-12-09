package com.vin.teja.Server1;

public class Startup {
	
	public Startup() {
		System.out.println("startup");
	}
	
	//@PostConstruct
	public void init() {
		
		final String loadBalancerIP = "http://localhost:8082";
		final String aliveRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:us=\"http://teja.vin.com/service\"><soapenv:Header/><soapenv:Body><us:AliveRequest><us:IPAddress>localhost</us:IPAddress><us:port>8082</us:port><us:serviceNames>AddService,MinusService</us:serviceNames></us:AliveRequest></soapenv:Body></soapenv:Envelope>";
		
		HTTPConnection http = new HTTPConnection(loadBalancerIP, aliveRequest);
		Thread t = new Thread(http);
		t.start();
	}
	
}
