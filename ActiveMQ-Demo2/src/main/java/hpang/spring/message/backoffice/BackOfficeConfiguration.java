package hpang.spring.message.backoffice;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import hpang.spring.message.MailMessageConverter;

@Configuration
//@Import(Spring5MessageV1Application.class)
public class BackOfficeConfiguration {
	@Autowired
	private ConnectionFactory connectionFactory;
	/*
	@Autowired
	Queue mailDestination;
	*/
	@Autowired
	MailMessageConverter mailMessageConverter;
	
	@Bean
	public JmsTemplate backOfficeJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		
		//jmsTemplate.setDefaultDestination(mailDestination);//It works
		jmsTemplate.setDefaultDestinationName("mail.queue");
		jmsTemplate.setMessageConverter(mailMessageConverter);
		jmsTemplate.setReceiveTimeout(10000);//Default will wait forever
		return jmsTemplate;
	}

	@Bean
	public BackOfficeImpl backOffice() {
		BackOfficeImpl backOffice = new BackOfficeImpl();
		backOffice.setJmsTemplate(backOfficeJmsTemplate());
		return backOffice;
	}
}
