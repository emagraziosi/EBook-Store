package com.emanuele.ebookStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EbookStoreApplication {

	public static void main(String[] args) {
		System.out.println("Ecchelo!");
		SpringApplication.run(EbookStoreApplication.class, args);
	}

}
