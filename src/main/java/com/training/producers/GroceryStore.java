package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.Items;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class GroceryStore {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);
        final String TOPIC = "grocery";
        int i = 0;

        Producer<String, String> producer = new KafkaProducer(props);
        while (true) {
            int partition = new Random()
                    .ints(0, Items.values().length)
                    .findFirst()
                    .getAsInt();
            String key = Items.values()[partition].toString();
            String value = key + "-" + i++;
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC, partition, key, value);
            producer.send(producerRecord, (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println(recordMetadata.toString());
                } else {
                    e.printStackTrace();
                }
                System.out.println("Key=" + key + ", Value =" + value);
            });

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}