package com.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;

/**
 *
 */
@Log4j2
@ConfigurationProperties("com.sample.spring.boot.apikey")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ApiKeyValidatorService {

  private ApiKeyValidatorBase apiKeyValidator;

  @Value("${com.sample.spring.boot.apikey.required:Unauthorized}")
  private String apiKeyRequiredError;

  @Value("${com.sample.spring.boot.apikey.invalid:Full authentication is required to access this resource (401.400)}")
  private String invalidApiKeyError;

  @Value("${com.sample.spring.boot.apikey.exception:Full authentication is required to access this resource (401.500)}")
  private String unexpectedApiKeyError;

  public ApiKeyValidatorService() {
    // default constructor
  }

  public boolean isEnabled() {
    return this.apiKeyValidator == null ? false : this.apiKeyValidator.isEnabled();
  }

  public String validateRequestApiKey(final String apiKey, String requestURI, String referrer) {

    log.debug("[ApiKeyValidatorBase] validateRequestApiKey()");

    if (this.isEnabled()) {
      log.debug("[ApiKeyValidatorService] API Key Enabled");

      if (apiKey == null || apiKey.equals("")) {
        log.warn("[ApiKeyValidatorService] Missing API Key HTTP Header");
        return this.apiKeyRequiredError;
      }

      try {
        if (!apiKeyValidator.validate(apiKey, requestURI, referrer)) {
          log.warn("[ApiKeyValidatorService] Invalid API Key: {}", new Object[] { apiKey });

          return this.invalidApiKeyError;
        }
      } catch (Exception macaroonException) {
        log.error("[ApiKeyValidatorService] API Key Error ({}): {}",
          new Object[] { apiKey, macaroonException.getMessage() });

        return this.unexpectedApiKeyError;
      }
    }

    return null;
  }

  public ApiKeyValidatorBase getApiKeyValidator() {
    return apiKeyValidator;
  }

  public void setApiKeyValidator(ApiKeyValidatorBase apiKeyValidator) {
    this.apiKeyValidator = apiKeyValidator;
  }
}
