package com.camel.xslttransform.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class TransformService {

	private static final String UTF_8 = "UTF-8";
	@Autowired
	ResourceLoader resourceLoader;

	public String gettransformer(String transfer, String mappingXslt)
			throws IOException, URISyntaxException, TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Resource resource = resourceLoader.getResource(mappingXslt);
		Transformer transformer = factory.newTransformer(new StreamSource(resource.getFile()));
		Source text = new StreamSource(new ByteArrayInputStream(transfer.getBytes(Charset.forName(UTF_8))));
		StringWriter outWriter = new StringWriter();
		StreamResult result = new StreamResult(outWriter);
		transformer.transform(text, result);
		return outWriter.getBuffer().toString();
	}

}
