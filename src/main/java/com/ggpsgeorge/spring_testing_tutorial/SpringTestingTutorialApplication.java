package com.ggpsgeorge.spring_testing_tutorial;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringTestingTutorialApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringTestingTutorialApplication.class, args);
	}

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}
