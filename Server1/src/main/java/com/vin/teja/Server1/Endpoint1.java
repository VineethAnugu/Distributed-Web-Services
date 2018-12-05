package com.vin.teja.Server1;

import com.vin.teja.service.Request;
import com.vin.teja.service.Response;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class Endpoint1 {

    @PayloadRoot(namespace = "http://teja.vin.com/service",    		
            localPart = "Request")
    @ResponsePayload
    public Response Request(@RequestPayload Request request) {
        Response response = new Response();
      //  String func = request.getFunction();
      //  if(func == "+") {
        float a = request.getNum1();
        float b = request.getNum2();
        response.setResNum(a+b);
        response.setComment("Success");
        return response;
        }
     /*   
        else if(func == "-") {
            float a = request.getNum1();
            float b = request.getNum2();
            response.setResNum(a-b);
            response.setComment("Success");
            return response;
            }
        
        else {
        	response.setComment("Service Doesnt Exist or Error Occured");
        	return response;
        }   */
    } 
