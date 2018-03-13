package hpang.spring.message;


import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@EnableTransactionManagement //If we need transaction for JMS
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
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory("tcp://localhost:61616");
	}

	//This is only needed if we want JMS transaction
	@Bean 
    public PlatformTransactionManager transactionManager() {
		/*
		 * If you require transaction management across multiple resources, 
		 * such as a data source and an ORM resource factory, 
		 * or if you need distributed transaction management, 
		 * you have to configure JTA transaction in your app server 
		 * and use JtaTransactionManager. Note that for multiple resource transaction support, 
		 * the JMS connection factory must be XA compliant 
		 * (i.e., it must support distributed transactions).
		 */
        return new JmsTransactionManager(connectionFactory());
    }

	
	@Bean
    public MailMessageConverter mailMessageConverter() {
        return new MailMessageConverter();
    }
	
	/* We can do by name instead of by instance
	@Bean
	public Queue mailDestination() {
		return new ActiveMQQueue("mail.queue");
	}
	*/
}
