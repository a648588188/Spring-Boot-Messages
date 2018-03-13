package hpang.spring.message.backoffice;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PreDestroy;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import hpang.spring.message.model.Mail;

@Service
public class BackOfficeImpl implements BackOffice {

	private static final String QUEUE_NAME = "mail.queue";
	private MailListener mailListener = new MailListener();
	private Connection connection;

	@Scheduled(initialDelay = 2000, fixedRate = 10000)
	@Override
	public Mail receiveMail() {

		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("localhost");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		connectionFactory.setPort(5672);

		Channel channel = null;

		try {
			connection = connectionFactory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);

			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					Mail mail = new ObjectMapper().readValue(body, Mail.class);
					mailListener.displayMail(mail);
				}
			};

			channel.basicConsume(QUEUE_NAME, true, consumer);

			return null;
		} catch (IOException | TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (IOException e) {
			}
		}
	}
}
