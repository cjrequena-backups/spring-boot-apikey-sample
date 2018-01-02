package com.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
@Log4j2
public class DefaultApiKeyValidator implements ApiKeyValidatorBase {

  private boolean enabled = true;

  private List<String> apiKeyList = new ArrayList<>();

  /**
   * Default validate method which accepts all requestURIs and referrers if
   * API KEY is permitted
   */
  @Override
  public boolean validate(String apiKey, String requestURI, String referrer) {
    // default api key validator accept all requestURI and referrer
    return apiKeyList.contains(apiKey);
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void addApiKey(String apiKey) {
    if (!apiKeyList.contains(apiKey)) {
      apiKeyList.add(apiKey);
    }
  }

  public void removeApiKey(String apiKey) {
    if (apiKeyList.contains(apiKey)) {
      apiKeyList.remove(apiKey);
    }
  }
}
