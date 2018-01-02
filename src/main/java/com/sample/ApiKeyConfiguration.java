package com.sample;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
@EnableConfigurationProperties(ApiKeyValidatorService.class)
public class ApiKeyConfiguration {

  @Bean(name = "apiKeyFilter")
  public Filter apiKeyFilter() {
    return new ApiKeyFilter(validatorService());
  }

  @Bean(name = "validatorService")
  public ApiKeyValidatorService validatorService() {
    return new ApiKeyValidatorService();
  }

  @Bean(name = "defaultApiKeyValidator")
  public DefaultApiKeyValidator getDefaultApiKeyValidator() {
    return new DefaultApiKeyValidator();
  }
}
