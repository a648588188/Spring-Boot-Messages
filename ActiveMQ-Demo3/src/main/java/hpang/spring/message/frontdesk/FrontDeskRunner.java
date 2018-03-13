package hpang.spring.message.frontdesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;

import hpang.spring.message.model.Mail;

public class FrontDeskRunner implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(FrontDeskRunner.class);

	@Autowired
	FrontDesk frontDesk;
	static int count =0;

	@Override
	public void run(String... strings) throws Exception {
		log.info("FrontDeskRunner start...");
	}
	
	@Scheduled(initialDelay = 10, fixedRate = 10000)
	public void sendMessage(){
		
		Mail mail = new Mail("Id"+count, "US", 1.5);
		frontDesk.sendMail(mail);
		count++;

		log.info("Mail #" + mail.getMailId() + " send");
	}
}
