package com.training.consumers;

import com.training.IKafkaConstants;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class OrdersConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"G0");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put("schema.registry.url", IKafkaConstants.SCHEMA_REGISTRY_URL);

        final String TOPIC = IKafkaConstants.ORDERS_TOPIC_NEW;

       // Consumer<String, KsqlDataSourceSchema> consumer = new KafkaConsumer(props) ;
        //consumer.subscribe(Collections.singleton(TOPIC));
        //TopicPartition topicPartition = new TopicPartition(TOPIC,0);

        while(true){
          //  consumer.poll(1000).forEach(record ->{
            //    System.out.println("Key=>"+record.key()+", Value =>"+record.value());

            //});
           //System.out.println("current offset position =>"+consumer.position(topicPartition));
        }
    }
}
