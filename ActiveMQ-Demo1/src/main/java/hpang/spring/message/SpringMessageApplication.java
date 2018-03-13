package hpang.spring.message;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import hpang.spring.message.backoffice.BackOfficeRunner;
import hpang.spring.message.frontdesk.FrontDeskRunner;

/*
 * http://activemq.apache.org/getting-started.html
 * 
 * To start ActiveMQ
 * bin\activemq start
 * http://localhost:8161/admin/
 * login: admin/admin
 * 
 * ActiveMQ exposes very useful beans and statistics from JMX
 * jconsole shows org.apache.activemq in MBean tab
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
