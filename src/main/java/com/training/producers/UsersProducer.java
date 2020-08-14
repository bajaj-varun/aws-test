package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.Items;
import com.training.pojos.Users;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class UsersProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //UserSerDe.class
        Producer<String, Users> producer = new KafkaProducer(props);
        IntStream.range(0,500).forEach(i->{
            int partition = new Random()
                    .ints(0, 1)
                    .findFirst()
                    .getAsInt();
            System.out.println(partition);
            Users users = new Users("Test -"+i,"red",i);
            ProducerRecord record = new ProducerRecord(IKafkaConstants.myusertest_TOPIC, partition,Integer.toString(i),users.toString());
            //ProducerRecord record1 = new ProducerRecord(
            System.out.println(i);
            try {
                // Fire and Forget Strategy
                // producer.send(record);

                // Async Method
                /*producer.send(record,(recordMetadata, e) -> {
                    System.out.println(recordMetadata.toString());
                    if(e!=null){
                        // Open filewrite
                        // Divert fail records
                    }
                });*/

                // Sync Strategy
               /* RecordMetadata metadata= (RecordMetadata) producer.send(record).get();
                System.out.println(metadata.toString());*/
                // Thread.sleep(10);
                producer.send(record,
                        (recordMetadata, e) -> {
                    // Something to do
                            System.out.println(recordMetadata.toString());

                        }
                    ).get();


            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
