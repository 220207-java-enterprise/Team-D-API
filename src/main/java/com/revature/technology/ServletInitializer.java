package com.revature.technology;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitialize extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.out.println("Yeah boyyy");
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TechnologyApplication.class);
	}

}
