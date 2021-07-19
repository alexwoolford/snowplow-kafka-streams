package io.woolford;

import cats.data.Validated;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowplowanalytics.snowplow.analytics.scalasdk.Event;
import com.snowplowanalytics.snowplow.analytics.scalasdk.ParsingError;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.Either;

import java.util.Properties;

public class SnowplowStreams {

    private static Logger logger = LoggerFactory.getLogger(SnowplowStreams.class);

    public static void main(String[] args) {

        logger.info("Starting Snowplow TSV to JSON Kafka Streams job");

        // set props for Kafka Steams app (see KafkaConstants)
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, KafkaConstants.APPLICATION_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        props.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, KafkaConstants.SECURITY_PROTOCOL);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, KafkaConstants.SASL_JAAS_CONFIG);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(SaslConfigs.SASL_MECHANISM, KafkaConstants.SASL_MECHANISM);

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> snowplowEnrichedGood = builder.stream("snowplow-enriched-good");

        snowplowEnrichedGood.mapValues(value -> {
            Validated<ParsingError, Event> event  = Event.parse(value);
            Either<ParsingError, Event> condition = event.toEither();
            if (condition.isLeft()){
                ParsingError err = condition.left().get();
                logger.error(err.toString());
                return null;
            } else {
                Event e = condition.right().get();

                ObjectMapper mapper = new ObjectMapper();
                String json = null;

                Object object = null;
                try {
                    object = mapper.readValue(e.toJson(false).deepDropNullValues().toString(), Object.class);
                    json = mapper.writeValueAsString(object);
                } catch (Exception exception) {
                    logger.error("Unable to parse JSON. " + exception.getMessage());
                }
                
                logger.info(json);
                return json;
            }
        }).to("snowplow-enriched-good-json");

        // run it
        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

}
