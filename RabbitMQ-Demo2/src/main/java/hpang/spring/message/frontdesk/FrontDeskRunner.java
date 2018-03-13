package hpang.spring.message.frontdesk;

import java.util.Locale;
import java.util.Random;

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
		Random rnd = new Random();
        int countries = Locale.getISOCountries().length;
        String country = Locale.getISOCountries()[rnd.nextInt(countries)];

        Mail mail = new Mail(String.valueOf(System.currentTimeMillis()), country, rnd.nextDouble());
        frontDesk.sendMail(mail);
		
		log.info("Send " +mail);
	}
}
