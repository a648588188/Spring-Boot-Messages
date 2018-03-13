package hpang.spring.message.backoffice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class BackOfficeRunner implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(BackOfficeRunner.class);
	
	@Override
	public void run(String... strings) throws Exception {
		//Thread.sleep(5000);
		log.info("BackOfficeRunner start...");
	}
}
