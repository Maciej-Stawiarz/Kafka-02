package ms.Kafka_02.consumer.model;

public record Food(
		Long id,
		String name,
		Double price,
		Taste taste) {

}