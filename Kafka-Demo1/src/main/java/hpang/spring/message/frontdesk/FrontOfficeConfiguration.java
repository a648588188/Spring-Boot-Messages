package hpang.spring.message.frontdesk;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class FrontOfficeConfiguration {

	/*
	 * KafkaTemplate implements KafkaOperations interface
	 * 
	 * ProducerFactory, KafkaTemplate have parameter <Integer, Object> 
	 * which means that we send message with Integer type as the key and message of type Object
	 * Now the value is Object type because we use JsonSerializer
	 */
	@Bean
    public KafkaTemplate<Integer, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

	@Bean
    public ProducerFactory<Integer, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactoryProperties());
    }
	
	@Bean
    public Map<String, Object> producerFactoryProperties() {
        Map<String, Object> properties = new HashMap<>();
        //You can add more servers to connect to
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //key is Integer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        
        //value is not String any more. we can send object
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }
	
    @Bean
    public FrontDeskImpl frontDesk() {
        return new FrontDeskImpl(kafkaTemplate());
    }
}
