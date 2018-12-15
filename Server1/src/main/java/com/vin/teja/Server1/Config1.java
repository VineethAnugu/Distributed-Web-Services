package com.vin.teja.Server1;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
@ComponentScan("com.vin.teja")
public class Config1 extends WsConfigurerAdapter {

		//creating the servlet
	@Bean("messageDispatcherServlet")
    public ServletRegistrationBean<FrameworkServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<FrameworkServlet>(servlet, "/*");
    }

	//Schema mapping
    @Bean
    public XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Services.xsd"));
    }

    //Handling the WSDL
    @Bean(name = "Server1")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema userSchema) {

        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchema(userSchema);
        definition.setLocationUri("/");
        definition.setPortTypeName("Server1Port");
        definition.setTargetNamespace("http://teja.vin.com/service");
        return definition;
    }

}