package com.camel.xslttransform.component;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camel.xslttransform.service.TransformService;

@Component
public class JmsRouterComponent extends RouteBuilder {

	@Autowired
	TransformService transformService;

	@Override
	public void configure() throws Exception {
		from("jms:DEV.QUEUE.1").log(LoggingLevel.DEBUG, log, "New message received").process(exchange -> {
			String payload = exchange.getIn().getBody(String.class);

			// For uppercase mapping xslt
			String mappingXslt = "classpath:transform/uppercase.xslt";
			String convertedMessage = transformService.gettransformer(payload, mappingXslt);

			exchange.getMessage().setBody(convertedMessage);
		}).to("jms:DEV.QUEUE.2").log(LoggingLevel.DEBUG, log, "Message is successfully sent to the output queue");

	}
}