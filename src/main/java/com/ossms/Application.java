package com.ossms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = { "staff.controller", "department.controller", "item.controller",
		"request.controller", "inheritance.controller" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}