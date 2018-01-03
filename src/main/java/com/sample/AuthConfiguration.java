package com.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class AuthConfiguration {

  /**
   *
   * @return
   */
  @Bean(name = "tokenValidatorService")
  public AuthValidatorService tokenValidatorService() {
    return new AuthValidatorService();
  }

  /**
   *
   * @return
   */
  @Bean(name = "tokenValidator")
  public TokenValidator tokenValidator() {
    return new TokenValidator();
  }
}
