package com.sample;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GreetingDTO {
  /**
   *
   */
  private final long id;
  /**
   *
   */
  private final String content;
}
