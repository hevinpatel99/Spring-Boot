package com.serviceRegistery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @EnableEurekaServer : This annotation will make this application to act as Microservice registry and discovery server.
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceRegisteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegisteryApplication.class, args);
    }

}
