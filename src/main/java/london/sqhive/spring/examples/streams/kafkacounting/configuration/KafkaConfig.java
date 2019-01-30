package london.sqhive.spring.examples.streams.kafkacounting.configuration;

import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class KafkaConfig {

  private static final String STORE_NAME = "counted-30-secs";

  @Lazy
  @Bean
  public ReadOnlyWindowStore<String, Long> store(
      InteractiveQueryService interactiveQueryService
  ) {
    return interactiveQueryService.getQueryableStore(
        STORE_NAME,
        QueryableStoreTypes.windowStore()
    );
  }

  @Bean
  public Materialized<String, Long, WindowStore<Bytes, byte[]>> state() {
    return Materialized.as(STORE_NAME);
  }
}
