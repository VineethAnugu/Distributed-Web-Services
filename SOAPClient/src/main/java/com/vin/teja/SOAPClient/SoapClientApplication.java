package com.vin.teja.SOAPClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.vin.teja.addservice.StudentDetailsRequest;
import com.vin.teja.addservice.StudentDetailsResponse;
 
@SpringBootApplication
public class SoapClientApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(SoapClientApplication.class, args);
    }
     
    @Bean
    CommandLineRunner lookup(SOAPConnector soapConnector) {
        return args -> {
            String name = "Sajal";//Default Name
            if(args.length>0){
                name = args[0];
            }
            AddRequest request = new AddRequest();
            request.setName(name);
            AddResponse response =(AddResponse) soapConnector.callWebService("http://localhost:8081/addservice", request);
            System.out.println("Got Response As below ========= : ");
            System.out.println("Result : "+response.getStudent().getName());
        };
    }
}