package hpang.spring.message;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import hpang.spring.message.model.Mail;

public class MailMessageConverter implements MessageConverter {
	int testCount = 0;
	
    private static final String WEIGHT = "weight";
	private static final String COUNTRY = "country";
	private static final String MAIL_ID = "mailId";

	public Object fromMessage(Message message) throws JMSException,
            MessageConversionException {
        MapMessage mapMessage = (MapMessage) message;
        Mail mail = new Mail();
        mail.setMailId(mapMessage.getString(MAIL_ID));
        mail.setCountry(mapMessage.getString(COUNTRY));
        mail.setWeight(mapMessage.getDouble(WEIGHT));
        
        testCount++;
        
        if(testCount % 10 ==1){
        	System.out.println("Mail ID: " + mail.getMailId() + " fail");
        	throw new JMSException("test transaction");
        }
        
        return mail;
    }

    public Message toMessage(Object object, Session session) throws JMSException,
            MessageConversionException {
        Mail mail = (Mail) object;
        MapMessage message = session.createMapMessage();
        message.setString(MAIL_ID, mail.getMailId());
        message.setString(COUNTRY, mail.getCountry());
        message.setDouble(WEIGHT, mail.getWeight());
        return message;
    }
}
