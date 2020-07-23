package com.training.consumers;

import com.training.pojos.Items;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

public class GroceryConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"G2");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        final String TOPIC = "grocery";

        Consumer<String, Integer> consumer = new KafkaConsumer(props, new StringDeserializer(), new StringDeserializer());
       consumer.subscribe(Collections.singleton(TOPIC));

        while(true){
            consumer.poll(1000).forEach(record ->{
                System.out.println(record.toString());
            });
        }
    }
}
