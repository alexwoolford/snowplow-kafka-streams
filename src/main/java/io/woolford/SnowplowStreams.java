package io.woolford;

import cats.data.Validated;
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
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "snowplow-kafka-streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("SNOWPLOW_KAFKA_BOOTSTRAP_SERVERS"));

        if (System.getenv("SNOWPLOW_KAFKA_SECURITY_PROTOCOL") != null){
            props.put(StreamsConfig.SECURITY_PROTOCOL_CONFIG, System.getenv("SNOWPLOW_KAFKA_SECURITY_PROTOCOL"));
        }

        if (System.getenv("SNOWPLOW_KAFKA_SASL_JAAS_CONFIG") != null){
            props.put(SaslConfigs.SASL_JAAS_CONFIG, System.getenv("SNOWPLOW_KAFKA_SASL_JAAS_CONFIG"));
        }

        if (System.getenv("SNOWPLOW_KAFKA_SASL_MECHANISM") != null){
            props.put(SaslConfigs.SASL_MECHANISM, System.getenv("SNOWPLOW_KAFKA_SASL_MECHANISM"));
        }

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> snowplowEnrichedGood = builder.stream("snowplow-enriched-good");

        snowplowEnrichedGood
                .filter((k, v) -> v != null)
                .mapValues(value -> {
            Validated<ParsingError, Event> event  = Event.parse(value);
            Either<ParsingError, Event> condition = event.toEither();
            if (condition.isLeft()){
                ParsingError err = condition.left().get();
                logger.error(err.toString());
                return null;
            } else {
                Event e = condition.right().get();
                return e.toJson(false).toString();
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
