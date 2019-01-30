package london.sqhive.spring.examples.streams.kafkacounting.functions;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

public class KeyMap implements KeyValueMapper<String, String, KeyValue<String, String>> {

  public KeyValue<String, String> apply(
    String key,
    String value
  ) {
    return new KeyValue<>(value, value);
  }
}
