package com.ggpsgeorge.spring_mockito_testing;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMockitoTestinglApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringMockitoTestinglApplication.class, args);
	}

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}
