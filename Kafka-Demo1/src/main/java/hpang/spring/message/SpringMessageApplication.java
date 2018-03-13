package hpang.spring.message;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import hpang.spring.message.backoffice.BackOfficeRunner;
import hpang.spring.message.frontdesk.FrontDeskRunner;

/*
 * Start zookeeper server
 * C:\zookeeper-3.4.10\bin\zkserver
 * 
 * Start Kafka:
 * C:\kafka_2.12-1.0.1:
 * .\bin\windows\kafka-server-start.bat .\config\server.properties
 * 
 * To run:
 * mvn spring-boot:run
 * Or package to jar:
 * mvn clean package
 */

@EnableKafka //Only for detect @KafkaListener
@EnableScheduling
@SpringBootApplication
public class SpringMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMessageApplication.class, args);
	}
	
	
	@Bean
	public BackOfficeRunner backOfficeRunner() {
		return new BackOfficeRunner();
	}
	
	@Bean
	public FrontDeskRunner frontDeskRunner() {
		return new FrontDeskRunner();
	}
}
