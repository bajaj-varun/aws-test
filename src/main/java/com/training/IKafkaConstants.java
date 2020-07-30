package com.training;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public interface IKafkaConstants {
    String BOOTSTRAP_SERVERS="DESKTOP-2CGEI1G.localdomain:9092";

    String T0_TOPIC = "T0";
    String USERS_TOPIC = "user";

    Class<StringDeserializer> stringDeserializer = StringDeserializer.class;
    Class<StringSerializer> stringSerializer     = StringSerializer.class;

    Class<IntegerDeserializer> intDeserializer = IntegerDeserializer.class;
    Class<IntegerSerializer> intSerializer     = IntegerSerializer.class;

    // Spark related settings
    String MASTER="local[3]";
}
