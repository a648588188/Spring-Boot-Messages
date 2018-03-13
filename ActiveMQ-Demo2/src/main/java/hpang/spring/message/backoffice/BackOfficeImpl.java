package hpang.spring.message.backoffice;

import org.springframework.jms.core.support.JmsGatewaySupport;
import org.springframework.transaction.annotation.Transactional;

import hpang.spring.message.model.Mail;

//@Component
public class BackOfficeImpl extends JmsGatewaySupport implements BackOffice {

	@Transactional
	public Mail receiveMail() {
		
		return (Mail) getJmsTemplate().receiveAndConvert();
		
		/*
		@SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) getJmsTemplate().receiveAndConvert();
        if(map == null){
        	return null;
        }
		Mail mail = new Mail();
        mail.setMailId((String) map.get("mailId"));
        mail.setCountry((String) map.get("country"));
        mail.setWeight((Double) map.get("weight"));
        return mail;
        */

        
		/*
		MapMessage message = (MapMessage) getJmsTemplate().receive();
		try {
			if (message == null) {
				return null;
			}
			Mail mail = new Mail();
			mail.setMailId(message.getString("mailId"));
			mail.setCountry(message.getString("country"));
			mail.setWeight(message.getDouble("weight"));
			return mail;
		} catch (JMSException e) {
			throw JmsUtils.convertJmsAccessException(e);
		}
		*/
	}

}
