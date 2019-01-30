package london.sqhive.spring.examples.streams.kafkacounting.service;

import london.sqhive.spring.examples.streams.kafkacounting.domain.Count;
import london.sqhive.spring.examples.streams.kafkacounting.functions.CountMap;
import london.sqhive.spring.examples.streams.kafkacounting.functions.KeyMap;
import london.sqhive.spring.examples.streams.kafkacounting.functions.SplitFlatMap;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@EnableBinding(KafkaStreamsProcessor.class)
public class WordAggregator {

  @Autowired
  private Materialized<String, Long, WindowStore<Bytes, byte[]>> state;

  @StreamListener("input")
  @SendTo("output")
  public KStream<String, Count> process(
      KStream<String, String> input
  ) {
    return input
        .flatMapValues(new SplitFlatMap())
        .map(new KeyMap())
        .groupByKey(Serialized.with(Serdes.String(), Serdes.String()))
        .windowedBy(TimeWindows.of(30000))
        .count(state)
        .toStream()
        .map(new CountMap());
  }

}
