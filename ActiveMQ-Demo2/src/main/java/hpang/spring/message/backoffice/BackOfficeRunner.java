package hpang.spring.message.backoffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;

import hpang.spring.message.model.Mail;

public class BackOfficeRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(BackOfficeRunner.class);
	
	@Autowired
	BackOffice backOffice;
	
	@Override
	public void run(String... strings) throws Exception {
		//Thread.sleep(5000);
		log.info("BackOfficeRunner start...");
	}
	
	@Scheduled(initialDelay = 2000, fixedRate = 5000)
	public void run(){
		Mail mail = backOffice.receiveMail();
		if(mail == null){
			log.info("Empty Mail received");
			return;
		}
		log.info("Mail #" + mail.getMailId() + " received");
	}
}
