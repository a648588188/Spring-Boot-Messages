package hpang.spring.message.frontdesk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import hpang.spring.message.model.Mail;

public class FrontDeskImpl implements FrontDesk {

	private static final Logger log = LoggerFactory.getLogger(FrontDeskImpl.class);
	
	private final KafkaOperations<Integer, Object> kafkaOperations;

	public FrontDeskImpl(KafkaOperations<Integer, Object> kafkaOperations) {
		this.kafkaOperations = kafkaOperations;
	}

	public void sendMail(final Mail mail) {
		
		/*
		 * Topic is "mails"
		 * Sending a message using Kafka is generally an async operation
		 */
		//ListenableFuture<SendResult<Integer, String>> future = kafkaOperations.send("mails", convertToJson(mail));
		
		//Since we use JsonSerializer, we send mail object instead of convering to String
		ListenableFuture<SendResult<Integer, Object>> future = kafkaOperations.send("mails", mail);
		
		/*
		 * You can call future.get() to make it a blocking operation
		 * Here we register ListenableFutureCallback to nofity in asyn way. More like node's promise
		 */
		future.addCallback(new ListenableFutureCallback<SendResult<Integer, Object>>() {

			@Override
			public void onFailure(Throwable ex) {
				ex.printStackTrace();
			}

			@Override
			public void onSuccess(SendResult<Integer, Object> result) {
				log.info("Result (success): " + result.getRecordMetadata());
			}
		});

	}
	
/*
	private String convertToJson(Mail mail) {
		try {
			return new ObjectMapper().writeValueAsString(mail);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}*/

}
