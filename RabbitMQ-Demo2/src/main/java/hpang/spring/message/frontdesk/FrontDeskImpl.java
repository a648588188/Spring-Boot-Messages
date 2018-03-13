package hpang.spring.message.frontdesk;

import org.springframework.amqp.rabbit.core.RabbitGatewaySupport;

import hpang.spring.message.model.Mail;

public class FrontDeskImpl extends RabbitGatewaySupport implements FrontDesk {


	public void sendMail(Mail mail) {
		getRabbitOperations().convertAndSend(mail);
	}

}
