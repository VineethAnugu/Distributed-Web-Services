package com.vin.teja.WebServices.AddEndpoint;

import com.vin.teja.addservice.AddRequest;
import com.vin.teja.addservice.AddResponse;
import com.vin.teja.WebServices.AddService.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AddEndpoint {

    @SuppressWarnings("unused")
	@Autowired
    private AddService addService;


    @PayloadRoot(namespace = "http://teja.vin.com/addservice",
            localPart = "addRequest")
    @ResponsePayload
    public AddResponse Request(@RequestPayload AddRequest request) {
        AddResponse response = new AddResponse();
    //    response.setUser(userService.getUsers(request.getName()));
        return response;
    } 
}