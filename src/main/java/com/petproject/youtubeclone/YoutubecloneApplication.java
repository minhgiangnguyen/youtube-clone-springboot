package com.petproject.youtubeclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
//@EnableAsync

//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.petproject.youtubeclone.repositories.jpa")
//@EnableElasticsearchRepositories(basePackages = "com.petproject.youtubeclone.repositories.elasticsearch")
public class YoutubecloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutubecloneApplication.class, args);
	}

}
