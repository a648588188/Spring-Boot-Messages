package hpang.spring.message.frontdesk;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
//@Import(Spring5MessageV1Application.class)
public class FrontOfficeConfiguration {
	
	@Autowired
	private ConnectionFactory connectionFactory;
	/*
	@Autowired
	Queue mailDestination;
	
	@Autowired
	MailMessageConverter mailMessageConverter;
	*/
	
	@Bean
	public JmsTemplate frontDeskJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		//jmsTemplate.setDefaultDestination(mailDestination);
		jmsTemplate.setDefaultDestinationName("mail.queue");
		//jmsTemplate.setMessageConverter(mailMessageConverter);

		return jmsTemplate;
	}

	@Bean
	public FrontDeskImpl frontDesk() {
		FrontDeskImpl frontDesk = new FrontDeskImpl();
		frontDesk.setJmsTemplate(frontDeskJmsTemplate());
		//frontDesk.setDestination(mailDestination);
		return frontDesk;
	}
}
