package london.sqhive.spring.examples.streams.kafkacounting.controller;

import london.sqhive.spring.examples.streams.kafkacounting.domain.Count;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/counts")
public class CountsController {

  @Lazy
  @Autowired
  private ReadOnlyWindowStore<String, Long> store;

  @GetMapping("/{id}")
  public Flux<Count> get(
    @PathVariable
    String id
  ) {
    WindowStoreIterator<Long> iterator = store.fetch(id, 0L, Instant.now().toEpochMilli());

    return Flux
      .fromIterable(() -> iterator)
      .map(entry -> Count
          .builder()
          .timestamp(new Date(entry.key))
          .key(id)
          .count(entry.value)
          .build());
  }
}
