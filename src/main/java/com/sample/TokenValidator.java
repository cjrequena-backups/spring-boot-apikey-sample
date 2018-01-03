package com.sample;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 */
@Log4j2
@Data
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class TokenValidator implements TokenValidatorBase {

  @Value("${token.validator.enabled:true}")
  private boolean enabled;

  private String secretKey;

  /**
   * Default validate method which accepts all requestURIs and referrers if
   * API KEY is permitted
   */
  @Override
  public boolean validate(String token, String requestURI, String referrer) {
    return JWTUtil.validateToken(secretKey, token);
  }

}
