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
    
    public void loadInitialize(int porttemp) {
    	int loadtemp = 0;
    	for(String key : services.keySet()) {
    		if(Arrays.asList(services.get(key).getPort()).contains(porttemp))
    			services.get(key).setLoad(loadtemp);
    	}
    }
    
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
		int load_t = whichrequest.getLoadInc();
		String [] temp =  new String [2];
		int i = 0;
		for(String key : services.keySet()) {
			if(Arrays.asList(services.get(key).getServiceNames()).contains(r)) {
				temp[i] = key;
				if(i == 0 && services.get(key).getLoad() == 0)
					services.get(key).setLoad(load_t + services.get(key).getLoad());
				i++;
			}
		}
		String serverInf = "";
 		for(int j = 0; j<temp.length;j++) {
			ServerInfo s = services.get(temp[j]);
			/*whichresponse.setIPAddress(s.getIpAddress());
			whichresponse.setPort(s.getPort());*/		
			serverInf = serverInf+s.getKey()+",";
		}
 		serverInf = serverInf.substring(0, (serverInf.length()-1));
 	//	whichresponse.setServers(serverInf);
 		String[] ser_inf = serverInf.split(",");
 		int min = Integer.MAX_VALUE;
 		String reqd_server = "";
 		for(int g = 0; g < ser_inf.length; g++) {
 			if(Arrays.asList(services.get(ser_inf[g]).getKey()).contains(ser_inf[g])) {
 				if(min > services.get(ser_inf[g]).getLoad())
 					min = services.get(ser_inf[g]).getLoad();
 			}
 		}
 		for(String key : services.keySet()) {
 			if(Arrays.asList(services.get(key).getLoad()).contains(min)) {
 				reqd_server = services.get(key).getKey();
 				break;
 			}
 		}
 		whichresponse.setServer(reqd_server);
 		for(String key : services.keySet()) {
 			if(Arrays.asList(services.get(key).getKey()).contains(reqd_server))
 				if(services.get(key).getLoad() != 1)
 					services.get(key).setLoad(load_t + services.get(key).getLoad());
 		}
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
		loadInitialize(port);
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
