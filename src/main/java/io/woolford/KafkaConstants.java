package io.woolford;

public interface KafkaConstants {

    public static String KAFKA_BROKERS = "pkc-lzvrd.us-west4.gcp.confluent.cloud:9092";
    public static String APPLICATION_ID = "snowplow-kafka-streams-1";
    public static String SECURITY_PROTOCOL = "SASL_SSL";
    public static String SASL_JAAS_CONFIG = "org.apache.kafka.common.security.plain.PlainLoginModule required username='*************' password='*************';";
    public static String SASL_MECHANISM = "PLAIN";

}
