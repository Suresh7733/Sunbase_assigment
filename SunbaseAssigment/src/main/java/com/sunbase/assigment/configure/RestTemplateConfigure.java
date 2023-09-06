package com.sunbase.assigment.configure;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfigure {
	  @Bean
	    public RestTemplate restTemplate() {
	        RestTemplate restTemplate = new RestTemplate();
	        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
	        messageConverters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
	        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
	        messageConverters.add(new ByteArrayHttpMessageConverter());

	        return restTemplate;
	    }
}
