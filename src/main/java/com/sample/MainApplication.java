package com.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "com.sample" })
public class MainApplication implements CommandLineRunner {

  @Autowired
  private ApiKeyValidatorService validatorService;

  /*
   * Also you can implement own ApiKeyValidator implements from
   * ApiKeyValidatorBase
   */
  @Autowired
  private DefaultApiKeyValidator apiKeyValidator;

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    apiKeyValidator.addApiKey("pepito");
    validatorService.setApiKeyValidator(apiKeyValidator);
  }
}
