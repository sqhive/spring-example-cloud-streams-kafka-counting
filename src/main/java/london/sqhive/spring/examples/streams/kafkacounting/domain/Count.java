package london.sqhive.spring.examples.streams.kafkacounting.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Count {

  private Date timestamp;

  private String key;

  private Long count;

}
