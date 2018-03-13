package hpang.spring.message;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import hpang.spring.message.backoffice.BackOfficeRunner;
import hpang.spring.message.frontdesk.FrontDeskRunner;

/*
 * https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 * https://www.rabbitmq.com/management.html
 * 
 * install erlang 20.0 (otp_win64_20.0.exe)
 * install RabbitMQ 3.7.4
 * RabbitMQ run as Window service
 * 
 * TO run UI tool
 * Enable management plugin:
 * In C:\RabbitMQ Server\rabbitmq_server-3.7.4\sbin>
 * Type: rabbitmq-plugins enable rabbitmq_management
 * 
 * Then: http://localhost:15672/
 * login: guest/guest
 * 
 * http://localhost:15672/api - restfule api to get json data
 * http://localhost:15672/cli (for admin need to download)
 * 
 * To run:
 * mvn spring-boot:run
 * Or package to jar:
 * mvn clean package
 */
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
