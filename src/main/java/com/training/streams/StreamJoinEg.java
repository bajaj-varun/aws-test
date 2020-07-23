package com.training.streams;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamJoinEg {
    private final String ORDER_TOPIC="orders";
    private final String USERS_TOPIC="users";

    public Properties getProperties(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"StreamJoinEg");
        //props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,"StreamJoinEg");
        //props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,"StreamJoinEg");

        return props;
    }

    public void setTopology(){
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> orderStream = builder.stream(ORDER_TOPIC);
        KStream<String, String> userStream = builder.stream(USERS_TOPIC);
    }

    public static void main(String[] args) {

    }
}
