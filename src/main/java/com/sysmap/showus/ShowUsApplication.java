package com.sysmap.showus;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ShowUs Project API",
				version = "1.0.0",
				description = "This social network project API is a project for the SysMap bootcamp",
				contact = @Contact(
						name = "Luis Felipe Gongora Garcia",
						email = "lfelipeggarcia@gmail.com"
				)
		)
)
public class ShowUsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowUsApplication.class, args);
	}

}
