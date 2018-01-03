package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.sample"})
public class MainApplication implements CommandLineRunner {

  @Autowired
  private AuthValidatorService validatorService;

  /*
   * Also you can implement own ApiKeyValidator implements from
   * TokenValidatorBase
   */
  @Autowired
  private TokenValidator tokenValidator;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    // the valid token with no expiration is eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTE0OTc0ODY4LCJzdWIiOiJjNmYxZmI1Ny00MGZmLTRmNTgtODMwMS0zY2I3NjRjNzBhY2UiLCJpc3MiOiJjanJlcXVlbmEifQ.RVKvafgB3AqZq29-yy9ZYb2fI9eMGJTf2LfH0pZkIng
    tokenValidator.setSecretKey("b7dc641f-b9ef-4650-afd3-b10d3ec15374");
    validatorService.setTokenValidator(tokenValidator);
  }
}
