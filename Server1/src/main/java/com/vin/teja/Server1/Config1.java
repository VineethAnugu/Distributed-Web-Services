package com.vin.teja.Server1;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
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
public class Config1 extends WsConfigurerAdapter {

	@Bean("messageDispatcherServlet")
    public ServletRegistrationBean<FrameworkServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        System.out.println("dis servlet");
        return new ServletRegistrationBean<FrameworkServlet>(servlet, "/*");
    }


    @Bean
    public XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("Services.xsd"));
    }

    @Bean(name = "Server1")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema userSchema) {

        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchema(userSchema);
        definition.setLocationUri("/");
        definition.setPortTypeName("Server1Port");
        definition.setTargetNamespace("http://teja.vin.com/service");
        System.out.println("server1");
        return definition;
    }

    @Bean
    @DependsOn("messageDispatcherServlet")
    public Startup startupSequence() {
    	return new Startup();
	}

}