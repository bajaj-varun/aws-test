package com.training.producers;

import com.training.IKafkaConstants;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Prod1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IKafkaConstants.stringSerializer);

        int count=0;
        Producer<String, String> producer = new KafkaProducer(props);

        while(true) {

            String str = "Test-Value-"+count++;//new Prod1().getRandomString();
            ProducerRecord<String, String> producerRecord = new ProducerRecord(IKafkaConstants.T0_TOPIC, "Test", str);
            producer.send(producerRecord);
            Thread.sleep(100);
        }
    }
}

    /*private String getRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }*/
