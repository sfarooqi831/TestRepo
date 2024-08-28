package com.alivia.bussiness.contributor;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContributor implements InfoContributor {

	@Autowired
	ApplicationContext context;

	
	@Override
	public void contribute(Builder builder) {

		builder.withDetail("context", generateContent());

	}

	protected Map<String, Object> generateContent() {
		Map<String, Object> contextMap = new TreeMap<>();

		contextMap.put("bean-defination-count", context.getBeanDefinitionCount());
		contextMap.put("bean-names", context.getBeanDefinitionNames());
		contextMap.put("application-startup", new Date(context.getStartupDate()));
		contextMap.put("application-context-impl", context.getClass().getCanonicalName());
		return contextMap;
	}

}
