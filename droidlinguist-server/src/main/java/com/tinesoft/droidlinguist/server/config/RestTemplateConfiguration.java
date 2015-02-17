package com.tinesoft.droidlinguist.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration
{

	@Bean
	public RestTemplate restTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate;
	}

}
