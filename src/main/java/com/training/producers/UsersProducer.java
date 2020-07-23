package com.training.producers;

import com.training.pojos.Users;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class UsersProducer {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerDe.class);
        final String TOPIC = "Users";

        Producer<String, Users> producer = new KafkaProducer(props);
        IntStream.range(0,10).forEach(i->{
            Users users = new Users("Test -"+i,"red",i);
            ProducerRecord record = new ProducerRecord(TOPIC, Integer.toString(i),users);
            System.out.println(i);
            try {
                RecordMetadata metadata= (RecordMetadata) producer.send(record).get();
                System.out.println(metadata.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
