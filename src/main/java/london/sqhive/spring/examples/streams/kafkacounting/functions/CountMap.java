package london.sqhive.spring.examples.streams.kafkacounting.functions;

import london.sqhive.spring.examples.streams.kafkacounting.domain.Count;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Windowed;

import java.util.Date;

public class CountMap implements KeyValueMapper<Windowed<String>, Long, KeyValue<String, Count>> {

  public KeyValue<String, Count> apply(
      Windowed<String> value,
      Long count
  ) {
    return new KeyValue<>(
        value.key(),
        Count
            .builder()
            .timestamp(new Date(value.window().end()))
            .key(value.key())
            .count(count)
            .build()
    );
  }
}
