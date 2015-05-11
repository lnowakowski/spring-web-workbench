package org.ln.spring.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@ComponentScan(basePackages = "org.ln.spring.web.ws")
@EnableWs
public class WsConfig extends WsConfigurerAdapter {
	@Value("classpath:ws/echo-ws.xsd")
	private Resource echoWsXsd;
	
	@Bean
	public XsdSchema echoWsSchema() {
		return new SimpleXsdSchema(echoWsXsd);
	}
	
	@Bean(name = "echo")
	public DefaultWsdl11Definition echoWsdl(XsdSchema echoWsSchema) {
		DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
		
		wsdl.setPortTypeName("EchoPort");
		wsdl.setTargetNamespace("http://ln.org/spring/ws/echo");
		wsdl.setLocationUri("/ws/echo");
		wsdl.setSchema(echoWsSchema);
		
		return wsdl;
	}
}
