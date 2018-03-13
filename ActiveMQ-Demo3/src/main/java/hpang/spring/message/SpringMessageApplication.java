package hpang.spring.message;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import hpang.spring.message.backoffice.BackOfficeRunner;
import hpang.spring.message.backoffice.MailListener;
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
@EnableJms //JMS listerner to detect @Jmslistener
@EnableTransactionManagement // If we need transaction for JMS
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

	@Bean
	public MailListener mailListener() {
		return new MailListener();
	}

	@Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }

	/*
	 * Spring provides several types of message listener containers for you 
	 * to choose from in the org.springframework.jms.listener package, 
	 * of which SimpleMessageListenerContainer and DefaultMessageListenerContainer 
	 * are the most commonly used. SimpleMessageListenerContainer is the
	 * simplest one that doesn’t support transactions. 
	 * If you have a transaction requirement in receiving messages,
	 * you have to use DefaultMessageListenerContainer.
	 */
	/* use without @jmslistener but MailListener need to implement MessageListener interface
	@Bean
	public Object container() {
		SimpleMessageListenerContainer smlc = new SimpleMessageListenerContainer();
		smlc.setConnectionFactory(connectionFactory());
		smlc.setDestinationName("mail.queue");
		smlc.setMessageListener(mailListener());
		return smlc;
	}
	*/
	
	/*
	 * use with @Jmslistener without the implement of MessageListener interface
	 * not support transaction
	 */
	/*
	@Bean
    public SimpleJmsListenerContainerFactory jmsListenerContainerFactory() {
        SimpleJmsListenerContainerFactory listenerContainerFactory = new SimpleJmsListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        
        listenerContainerFactory.setMessageConverter(mailMessageConverter());
        
        return listenerContainerFactory;
    }
*/
	/*
	 * use with @Jmslistener without the implement of MessageListener interface
	 * support local JMS transaction
	 * if you want your listener to participate in a JTA transaction, you need to declare a
	 * JtaTransactionManager instance
	 */
	@Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(cachingConnectionFactory());
        listenerContainerFactory.setMessageConverter(mailMessageConverter());
        
        listenerContainerFactory.setSessionTransacted(true);
        
        return listenerContainerFactory;
    }

	

	// This is only needed if we want JMS transaction
	@Bean
	public PlatformTransactionManager transactionManager() {
		/*
		 * If you require transaction management across multiple resources, such
		 * as a data source and an ORM resource factory, or if you need
		 * distributed transaction management, you have to configure JTA
		 * transaction in your app server and use JtaTransactionManager. Note
		 * that for multiple resource transaction support, the JMS connection
		 * factory must be XA compliant (i.e., it must support distributed
		 * transactions).
		 */
		return new JmsTransactionManager(connectionFactory());
	}

	
	@Bean
	public MailMessageConverter mailMessageConverter() {
		return new MailMessageConverter();
	}

	/*
	 * We can do by name instead of by instance
	 * 
	 * @Bean public Queue mailDestination() { return new
	 * ActiveMQQueue("mail.queue"); }
	 */
}
