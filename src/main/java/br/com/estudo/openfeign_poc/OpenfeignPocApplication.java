package br.com.estudo.openfeign_poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.estudo.openfeign_poc.v1")

public class OpenfeignPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfeignPocApplication.class, args);
	}

}
