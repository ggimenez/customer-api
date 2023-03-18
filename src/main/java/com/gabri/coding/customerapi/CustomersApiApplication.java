package com.gabri.coding.customerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.modelmapper.ModelMapper;

@SpringBootApplication
@EnableSwagger2
public class CustomersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApiApplication.class, args);
	}

	/*@Bean
	public Docket customerApiSwagger() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.gabri.coding.controller")).build();
	}*/

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}


