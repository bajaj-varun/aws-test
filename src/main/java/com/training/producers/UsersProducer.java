package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.Users;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class UsersProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerDe.class);

        Producer<String, Users> producer = new KafkaProducer(props);
        IntStream.range(0,500).forEach(i->{
            Users users = new Users("Test -"+i,"red",i);
            ProducerRecord record = new ProducerRecord(IKafkaConstants.USERS_TOPIC, Integer.toString(i),users);
            System.out.println(i);
            try {
                RecordMetadata metadata= (RecordMetadata) producer.send(record).get();
                System.out.println(metadata.toString());
               // Thread.sleep(10);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
