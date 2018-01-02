package com.sample;

/**
 *
 */
public interface ApiKeyValidatorBase {
  /**
   * Validate API Key with rest URL and web site
   *
   * @param apiKey
   *            used API Key for validation
   * @param requestURI
   *            Requested rest URL
   * @param referrer
   *            Domain or IP address of the requested client
   * @return true, if validation successful, else false
   */
  boolean validate(String apiKey, String requestURI, String referrer);

  /**
   * Check API key validation is enabled or not
   *
   * @return true if enabled, else false
   */
  boolean isEnabled();
}
