package com.training.consumers;

import com.training.IKafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"TG0");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IKafkaConstants.stringDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IKafkaConstants.stringDeserializer);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        Consumer<String, String> consumer = new KafkaConsumer(props) ;
        consumer.subscribe(Collections.singleton(IKafkaConstants.T0_TOPIC));

        while(true){
            consumer.poll(100).forEach(record ->{
                System.out.println(record.key()+","+record.value());
                /*consumer.commitAsync((offset, e) -> {
                    if(e == null)
                        System.out.println(offset.toString());
                });*/
            });
        }
    }
}
