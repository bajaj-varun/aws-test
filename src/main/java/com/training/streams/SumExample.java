package com.training.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class SumExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"oddsum");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");
        props.put("default.deserialization.exception.handler", LogAndContinueExceptionHandler.class);

        final String NUMBERS_TOPIC = "numberTopic";
        final String SUM_OF_ODD_NUMBERS_TOPIC = "SUM_OF_ODD_NUMBERS_TOPIC";

        StreamsBuilder builder = new StreamsBuilder();
        KStream<Integer, Integer> input = builder.stream(NUMBERS_TOPIC);

        input.print(Printed.toSysOut());
        final KTable<Integer, Integer> sumOfOddNumbers = input
                .filter((k,v)->v%2!=0)
                .selectKey((k,v)->1)
                .groupByKey()
                .reduce((v1,v2)->v1+v2)
                ;

        sumOfOddNumbers.toStream().to(SUM_OF_ODD_NUMBERS_TOPIC, Produced.with(Serdes.Integer(),Serdes.Integer()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.cleanUp();
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}