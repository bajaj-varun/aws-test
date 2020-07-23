package com.training.producers;

import com.training.IKafkaConstants;
import com.training.pojos.generatedSources.RawMovie;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import java.util.ArrayList;
import java.util.Properties;

public class MovieProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url","http://localhost:8081");
        final String TOPIC = "movies";

        Producer<Long, RawMovie> producer = new KafkaProducer(props);
        ArrayList<RawMovie> moviesList = new ArrayList();
        moviesList.add(
                RawMovie.newBuilder()
                        .setId(1L)
                        .setTitle("Tannu weds Mannu")
                        .setGenre("rommantic")
                        .build());
        moviesList.add(
                RawMovie.newBuilder()
                        .setId(2L)
                        .setTitle("Spiderman")
                        .setGenre("kids and family")
                        .build());

        // Async send
        for(RawMovie rw : moviesList){
            producer.send(new ProducerRecord(TOPIC,rw.getId(),rw));
        }

        producer.close();
    }
}
