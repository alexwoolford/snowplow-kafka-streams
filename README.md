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



