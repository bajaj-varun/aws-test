package com.training;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public interface IKafkaConstants {
    public String BOOTSTRAP_SERVERS="13.235.90.108:9092";
    public String T0_TOPIC = "T0";

    public Class<StringDeserializer> stringDeserializer = StringDeserializer.class;
    public Class<StringSerializer> stringSerializer     = StringSerializer.class;

    public Class<IntegerDeserializer> intDeserializer = IntegerDeserializer.class;
    public Class<IntegerSerializer> intSerializer     = IntegerSerializer.class;
}
