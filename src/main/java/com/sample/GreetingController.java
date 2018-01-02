package com.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Slf4j
@RestController
public class GreetingController {
  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/")
  public ResponseEntity<Object> defaultError() {
    return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
  }

  @RequestMapping("/greetings")
  public GreetingDTO greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new GreetingDTO(counter.incrementAndGet(), String.format(TEMPLATE, name));
  }

  @RequestMapping("/greetings/{name}")
  public GreetingDTO greetingWitId(@PathVariable String name) {
    return new GreetingDTO(counter.incrementAndGet(), String.format(TEMPLATE, name));
  }
}
