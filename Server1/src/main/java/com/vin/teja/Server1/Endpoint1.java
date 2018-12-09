package com.vin.teja.Server1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.vin.teja.service.AddRequest;
import com.vin.teja.service.AliveRequest;
import com.vin.teja.service.AliveResponse;
import com.vin.teja.service.DeadRequest;
import com.vin.teja.service.DeadResponse;
import com.vin.teja.service.MinusRequest;
import com.vin.teja.service.Response;
import com.vin.teja.service.WhichRequest;
import com.vin.teja.service.WhichResponse;

@Endpoint
public class Endpoint1 {
	
    private Map<String, ServerInfo> services = new HashMap<>();
    
    	// AddRequest 
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "AddRequest")
	@ResponsePayload
	public Response AddRequest(@RequestPayload AddRequest addrequest) {
		Response response = new Response();

		float a = addrequest.getNum1();
		float b = addrequest.getNum2();
		response.setResNum(a+b);
		response.setComment("Success");
		return response;
	}

		// Minus Request
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "MinusRequest")
	@ResponsePayload
	public Response MinusRequest(@RequestPayload MinusRequest minusrequest) {
		Response response = new Response();

		float a = minusrequest.getNum1();
		float b = minusrequest.getNum2();
		response.setResNum(a-b);
		response.setComment("Success");
		return response;
	}
	
		// Which Request
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "WhichRequest")
	@ResponsePayload
	public WhichResponse WhichRequest(@RequestPayload WhichRequest whichrequest) {
		WhichResponse whichresponse = new WhichResponse();
		String r = whichrequest.getServiceName();
		String temp = "";
		for(String key : services.keySet()) {
			if(Arrays.asList(services.get(key).getServiceNames()).contains(r)) {
				temp = key;
				break;
			}
		}
		ServerInfo s = services.get(temp);
		whichresponse.setIPAddress(s.getIpAddress());
		whichresponse.setPort(s.getPort());		
		return whichresponse;
	}
	
	
		//Alive Request
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "AliveRequest")
	@ResponsePayload
	public AliveResponse AliveRequest(@RequestPayload AliveRequest aliverequest) {
		AliveResponse aliveresponse = new AliveResponse();
		String ip_add = aliverequest.getIPAddress();
		int port = aliverequest.getPort();
		String service_names = aliverequest.getServiceNames();
		String serv_names[] = service_names.split(",");
		ServerInfo s = new ServerInfo();
		s.setIpAddress(ip_add);
		s.setPort(port);
		s.setServiceNames(serv_names);
		this.services.put(s.getKey(), s);
		aliveresponse.setStatus("Stored");
		return aliveresponse;
	}
	
	
		//Dead Request
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "DeadRequest")
	@ResponsePayload
	public DeadResponse DeadRequest(@RequestPayload DeadRequest deadrequest) {
		DeadResponse deadresponse = new DeadResponse();
		String a = deadrequest.getIPAddress();
		int b = deadrequest.getPort();
		String key = a+":"+b;
		if( this.services.containsKey(key) ) {
			this.services.remove(key);
			deadresponse.setComment("Successfully Removed");
		}
		else
			deadresponse.setComment("Server information of the retrieved IP address and port is not present in the registry.");
		return deadresponse;
	}

}
