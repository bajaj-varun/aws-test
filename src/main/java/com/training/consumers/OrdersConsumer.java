package com.training.consumers;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.training.pojos.Orders;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Collections;
import java.util.Properties;

public class OrdersConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"G0");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        final String TOPIC = "orders";

        Consumer<String, Orders> consumer = new KafkaConsumer(props, new StringDeserializer(), new JsonDeserializer(Orders.class)) ;
        consumer.subscribe(Collections.singleton(TOPIC));
        //TopicPartition topicPartition = new TopicPartition(TOPIC,0);

        while(true){
            consumer.poll(1000).forEach(record ->{
                System.out.println("Key=>"+record.key()+", Value =>"+record.value());
            });
           //System.out.println("current offset position =>"+consumer.position(topicPartition));
        }
    }
}
