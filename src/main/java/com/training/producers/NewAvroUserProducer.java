package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.generatedSources.PojoUserAvro;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NewAvroUserProducer {

    public static void main(String[] args) {
        Properties props  = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);

        Producer<String, PojoUserAvro> producer = new KafkaProducer<String, PojoUserAvro>(props);
        PojoUserAvro u1 = new PojoUserAvro();
        u1.setName("Rajan");
        u1.setFavoriteNumber(5);
        u1.setFavoriteColor("Red");
        u1.setLanguage("C++");
        u1.setHobbies("Football");

        ProducerRecord<String, PojoUserAvro> pr1 = new ProducerRecord(IKafkaConstants.AVRO_USER_TOPIC, "test", u1);
        producer.send(pr1,(recordMetadata, e) -> {
            System.out.println(recordMetadata.toString());
        });
    }
}
