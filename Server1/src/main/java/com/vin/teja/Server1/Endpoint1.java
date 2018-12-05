package com.vin.teja.Server1;

import com.vin.teja.service.AddRequest;
import com.vin.teja.service.MinusRequest;
import com.vin.teja.service.Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class Endpoint1 {

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
}

