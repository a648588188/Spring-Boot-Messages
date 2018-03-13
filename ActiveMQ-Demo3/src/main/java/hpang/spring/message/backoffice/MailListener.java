package hpang.spring.message.backoffice;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import hpang.spring.message.model.Mail;

public class MailListener {
	private static final Logger log = LoggerFactory.getLogger(MailListener.class);
	
	@JmsListener(destination = "mail.queue")
    public void displayMail(Mail mail) {
        System.out.println("Mail #" + mail.getMailId() + " received");
    }

	//It works without using message converter
	//@JmsListener(destination = "mail.queue")
	public void displayMail(Map<String, Object> map) {
        Mail mail = new Mail();
        mail.setMailId((String) map.get("mailId"));
        mail.setCountry((String) map.get("country"));
        mail.setWeight((Double) map.get("weight"));
        log.info("Mail #" + mail.getMailId() + " received");
    }
}
