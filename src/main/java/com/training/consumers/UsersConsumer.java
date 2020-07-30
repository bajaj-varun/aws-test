package com.training.consumers;

import com.training.IKafkaConstants;
import com.training.serde.UserSerDe;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class UsersConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"TG1");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserSerDe.class);

        Consumer<String, Integer> consumer = new KafkaConsumer(props) ;
        consumer.subscribe(Collections.singleton(IKafkaConstants.USERS_TOPIC));

        while(true){
            consumer.poll(1000).forEach(record ->{
                System.out.println(record.key()+", Value =>"+record.value());
            });
        }
    }

}
