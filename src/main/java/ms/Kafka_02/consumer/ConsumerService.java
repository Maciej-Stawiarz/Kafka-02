package ms.Kafka_02.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	
	@Value(value = "${spring.kafka.consumer.topic-name}")
	private String topicName;
	
	@KafkaListener(topics = "food-topic", groupId = "default")
	public void consumerKafkaMessages(ConsumerRecord<?, ?> consumerRecord) {
		System.out.println("\nKey: " + consumerRecord.key() + " \nValue: " + consumerRecord.value());
	}
}
