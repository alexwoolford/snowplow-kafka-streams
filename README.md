# Snowplow Kafka Streams

This Kafka Streams job reads TSV data from a topic and converts those records to JSON.

![snowplow-arch](img/snowplow-arch.png)

Here's an example record from a page request:

    {
      "app_id": "woolford.io",
      "platform": "web",
      "etl_tstamp": "2020-10-14T05:48:40.212Z",
      "collector_tstamp": "2020-10-14T05:48:39.702Z",
      "dvce_created_tstamp": "2020-10-14T05:48:39.273Z",
      "event": "page_view",
      "event_id": "fe1afd98-d2d8-4d2c-8b07-07906236bfe3",
      "name_tracker": "cf",
      "v_tracker": "js-2.7.2",
      "v_collector": "ssc-1.0.1-kafka",
      "v_etl": "stream-enrich-1.1.0-common-1.1.0",
      "user_ipaddress": "67.49.43.165",
      "user_fingerprint": "2624941115",
      "domain_userid": "8077b07e-2fd3-4f98-a967-27504618f8a5",
      "domain_sessionidx": 5,
      "network_userid": "01bd2d58-dd76-4dfb-8f8d-395fe6d0ec1b",
      "geo_country": "US",
      "geo_region": "CA",
      "geo_city": "Indio",
      "geo_zipcode": "92201",
      "geo_latitude": 33.7209,
      "geo_longitude": -116.2172,
      "geo_region_name": "California",
      "page_url": "https://woolford.io/2019-12-11-zeek-neo4j/",
      "page_title": "Zeek, Kafka, and Neo4j",
      "page_urlscheme": "https",
      "page_urlhost": "woolford.io",
      "page_urlport": 443,
      "page_urlpath": "/2019-12-11-zeek-neo4j/",
      "contexts": {
        
      },
      "useragent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36",
      "br_lang": "en-US",
      "br_features_pdf": true,
      "br_features_flash": false,
      "br_features_java": false,
      "br_features_director": false,
      "br_features_quicktime": false,
      "br_features_realplayer": false,
      "br_features_windowsmedia": false,
      "br_features_gears": false,
      "br_features_silverlight": false,
      "br_cookies": true,
      "br_colordepth": "30",
      "br_viewwidth": 1536,
      "br_viewheight": 826,
      "os_timezone": "America/Los_Angeles",
      "dvce_screenwidth": 1536,
      "dvce_screenheight": 960,
      "doc_charset": "UTF-8",
      "doc_width": 1536,
      "doc_height": 9266,
      "geo_timezone": "America/Los_Angeles",
      "dvce_sent_tstamp": "2020-10-14T05:48:39.274Z",
      "derived_contexts": {
        "schema": "iglu:com.snowplowanalytics.snowplow/contexts/jsonschema/1-0-0",
        "data": [
          {
            "schema": "iglu:nl.basjes/yauaa_context/jsonschema/1-0-0",
            "data": {
              "deviceBrand": "Apple",
              "deviceName": "Apple Macintosh",
              "layoutEngineNameVersion": "Blink 86.0",
              "operatingSystemNameVersion": "Mac OS X 10.15.7",
              "layoutEngineNameVersionMajor": "Blink 86",
              "operatingSystemName": "Mac OS X",
              "agentVersionMajor": "86",
              "layoutEngineVersionMajor": "86",
              "deviceClass": "Desktop",
              "agentNameVersionMajor": "Chrome 86",
              "operatingSystemClass": "Desktop",
              "layoutEngineName": "Blink",
              "agentName": "Chrome",
              "agentVersion": "86.0.4240.75",
              "layoutEngineClass": "Browser",
              "agentNameVersion": "Chrome 86.0.4240.75",
              "operatingSystemVersion": "10.15.7",
              "deviceCpu": "Intel",
              "agentClass": "Browser",
              "layoutEngineVersion": "86.0"
            }
          },
          {
            "schema": "iglu:org.ietf/http_cookie/jsonschema/1-0-0",
            "data": {
              "name": "_ga",
              "value": "GA1.2.1445978790.1601936804"
            }
          },
          {
            "schema": "iglu:org.ietf/http_cookie/jsonschema/1-0-0",
            "data": {
              "name": "_pin_unauth",
              "value": "dWlkPU9XRTFNR1UwWVdZdFl6TTBZeTAwTURnMUxUaGxOVFF0T0RBMVpEUmpNR1kwWm1Reg"
            }
          },
          {
            "schema": "iglu:org.ietf/http_cookie/jsonschema/1-0-0",
            "data": {
              "name": "sp",
              "value": "01bd2d58-dd76-4dfb-8f8d-395fe6d0ec1b"
            }
          },
          {
            "schema": "iglu:org.ietf/http_cookie/jsonschema/1-0-0",
            "data": {
              "name": "__qca",
              "value": "P0-24455003-1601960898561"
            }
          },
          {
            "schema": "iglu:org.ietf/http_cookie/jsonschema/1-0-0",
            "data": {
              "name": "_gid",
              "value": "GA1.2.2031388627.1602612164"
            }
          }
        ]
      },
      "domain_sessionid": "74af090e-f2dd-4b7a-bbb1-0fd88333a959",
      "derived_tstamp": "2020-10-14T05:48:39.701Z",
      "event_vendor": "com.snowplowanalytics.snowplow",
      "event_name": "page_view",
      "event_format": "jsonschema",
      "event_version": "1-0-0"
    }

The Kafka Streams job was packaged in a Docker container. To run, pass in the following properties as environment variables:

- SNOWPLOW_KAFKA_BOOTSTRAP_SERVERS
- SNOWPLOW_KAFKA_SECURITY_PROTOCOL
- SNOWPLOW_KAFKA_SASL_JAAS_CONFIG
- SNOWPLOW_KAFKA_SASL_MECHANISM

This could be done by creating a `.env` file, e.g. `snowplow-ccloud.env` that contains the connection properties:

    SNOWPLOW_KAFKA_BOOTSTRAP_SERVERS=pkc-lzvrd.us-west4.gcp.confluent.cloud:9092
    SNOWPLOW_KAFKA_SECURITY_PROTOCOL=SASL_SSL
    SNOWPLOW_KAFKA_SASL_JAAS_CONFIG=org.apache.kafka.common.security.plain.PlainLoginModule required username='********' password='********';
    SNOWPLOW_KAFKA_SASL_MECHANISM=PLAIN

... and then launch the container:

    docker run -d --env-file snowplow-ccloud.env alexwoolford/snowplow-kafka-streams:latest

Once the data is in Kafka, we can build a graph of the `network_userid`'s and `page_url`'s

    http PUT cp01.woolford.io:8083/connectors/snowplow-neo4j/config <<< '
    {
        "connector.class": "streams.kafka.connect.sink.Neo4jSinkConnector",
        "name": "snowplow-neo4j",
        "neo4j.authentication.basic.password": "V1ctoria",
        "neo4j.authentication.basic.username": "neo4j",
        "neo4j.server.uri": "bolt://neo4j-snowplow.woolford.io:7687",
        "neo4j.topic.cypher.snowplow-enriched-json-good": "MERGE (network_userid:network_userid {id: event.network_userid}) MERGE (page_url:page_url {id: event.page_url}) MERGE (network_userid)-[:VIEWED]->(page_url)",
        "topics": "snowplow-enriched-json-good",
        "key.converter": "org.apache.kafka.connect.storage.StringConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter.schemas.enable": "false"
    }'

This graph can be queried, using the Cypher query language, to create personalized recommendations. The query can easily be exposed as a REST service, e.g.:

    #!/usr/bin/env python
    from neo4j import GraphDatabase
    from flask import Flask, jsonify
    
    app = Flask(__name__)
    
    
    @app.route('/recommendations/<network_userid>', methods=['GET'])
    def index(network_userid):
        recommendations = recommender.recommend(network_userid)
        return jsonify(recommendations)
    
    
    class Recommender:
    
        def __init__(self, uri, user, password):
            self.driver = GraphDatabase.driver(uri, auth=(user, password))
    
        def close(self):
            self.driver.close()
    
        def recommend(self, network_userid):
            with self.driver.session() as session:
                recommendations = session.read_transaction(self._get_recommendations, network_userid)
                return recommendations
    
        @staticmethod
        def _get_recommendations(tx, network_userid):
    
            query = """MATCH (user:network_userid {id: $network_userid})-[:VIEWED]->(page:page_url)<-[:VIEWED]-(other_user:network_userid)-[:VIEWED]->(other_page:page_url)
                       WHERE user <> other_user
                       AND NOT EXISTS ( ( {id: $network_userid}) -[:VIEWED]->(other_page:page_url) )
                       AND other_page.id <> "https://woolford.io/"
                       AND NOT other_page.id STARTS WITH "https://woolford.io/tags/"
                       WITH other_page.id AS page_url, COUNT(other_user) AS frequency
                       ORDER BY frequency DESC
                       RETURN page_url"""
    
            query_result = tx.run(query, network_userid=network_userid)
    
            recommendations = []
            for record in query_result:
                recommendations.append(record.get("page_url"))
            return recommendations
    
    
    if __name__ == "__main__":
        recommender = Recommender("bolt://neo4j-snowplow.woolford.io:7687", "neo4j", "V1ctoria")
        app.run()

Here's an example call to the recommender REST service:

    http localhost:5000/recommendations/8a5107ba-bffa-47de-ba9a-6fc74f08ac62
    [
        "https://woolford.io/2018-02-11-cowrie/",
        "https://woolford.io/2020-07-11-streaming-joins/"
    ]


[//]: # (TODO: mention the cold-start issue, i.e. the "green Volvo" problem)

