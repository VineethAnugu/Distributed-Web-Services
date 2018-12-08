package com.vin.teja.Server2;

import com.vin.teja.service.MulRequest;
import com.vin.teja.service.DivRequest;
import com.vin.teja.service.Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class Endpoint2 {

	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "MulRequest")
	@ResponsePayload
	public Response MulRequest(@RequestPayload MulRequest mulrequest) {
		Response response = new Response();

		float a = mulrequest.getNum1();
		float b = mulrequest.getNum2();
		response.setResNum(a*b);
		response.setComment("Success");
		return response;
	}

	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "DivRequest")
	@ResponsePayload
	public Response DivRequest(@RequestPayload DivRequest divrequest) {
		Response response = new Response();

		float a = divrequest.getNum1();
		float b = divrequest.getNum2();
		response.setResNum(a/b);
		response.setComment("Success");
		return response;
	}
}

