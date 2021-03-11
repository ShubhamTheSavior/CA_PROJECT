package com.ca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

@EntityScan(basePackages = {"com.ca.entitis"})
@SpringBootApplication
public class CaClientProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaClientProjectApplication.class, args);
	}

}
