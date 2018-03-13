package hpang.spring.message.frontdesk;

import org.springframework.jms.core.support.JmsGatewaySupport;
import org.springframework.transaction.annotation.Transactional;

import hpang.spring.message.model.Mail;

//@Component
public class FrontDeskImpl extends JmsGatewaySupport implements FrontDesk {

	@Transactional
	public void sendMail(final Mail mail) {
		getJmsTemplate().convertAndSend(mail);
		
		/*
		Map<String, Object> map = new HashMap<>();
        map.put("mailId", mail.getMailId());
        map.put("country", mail.getCountry());
        map.put("weight", mail.getWeight());
        getJmsTemplate().convertAndSend(map);
        */

		/* It works
		getJmsTemplate().send(session -> {
			MapMessage message = session.createMapMessage();
			message.setString("mailId", mail.getMailId());
			message.setString("country", mail.getCountry());
			message.setDouble("weight", mail.getWeight());
			return message;
		});
		*/
	}
}
