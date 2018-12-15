package com.vin.teja.Server4;


import com.vin.teja.service.DivRequest;
import com.vin.teja.service.MinusRequest;
import com.vin.teja.service.Response;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class Endpoint4 {
	


	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "DivRequest")
	@ResponsePayload
	public Response AddRequest(@RequestPayload DivRequest divrequest) {
		Response response = new Response();

		float a = divrequest.getNum1();
		float b = divrequest.getNum2();
		response.setResNum(a/b);
		response.setComment("Success");
		return response;
	}

	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "MinusRequest")
	@ResponsePayload
	public Response MulRequest(@RequestPayload MinusRequest minusrequest) {
		Response response = new Response();

		float a = minusrequest.getNum1();
		float b = minusrequest.getNum2();
		response.setResNum(a-b);
		response.setComment("Success");
		return response;
	}
	
	
}

