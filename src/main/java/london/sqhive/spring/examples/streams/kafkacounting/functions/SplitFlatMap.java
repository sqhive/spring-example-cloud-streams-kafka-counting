package london.sqhive.spring.examples.streams.kafkacounting.functions;

import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.Arrays;
import java.util.List;

public class SplitFlatMap implements ValueMapper<String, List<String>> {

  public List<String> apply(
      String value
  ) {
    return Arrays.asList(value.toLowerCase().split("\\W+"));
  }
}
