package ms.Kafka_02.configs;

import ms.Kafka_02.consumer.model.Food;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value(value = "${spring.kafka.consumer.bootstrap-server}")
	private String bootstrapAddress;
	@Value(value = "${spring.kafka.consumer.consumer-group-id}")
	private String consumerGroupID;
	
	@Bean
	public ConsumerFactory<String, Food> consumerFactory() {
		Map<String, Object> configurationProperties = new HashMap<>();
		configurationProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configurationProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupID);
		configurationProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configurationProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
		
		try (JacksonJsonDeserializer<Food> jsonDeserializer = new JacksonJsonDeserializer<>(Food.class)) {
			jsonDeserializer.setUseTypeHeaders(false);
			
			return new DefaultKafkaConsumerFactory<>(
					configurationProperties,
					new StringDeserializer(),
					jsonDeserializer
			);
		}
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Food> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Food> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}