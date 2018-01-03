package com.sample;

/**
 *
 */
public interface TokenValidatorBase {
  /**
   *
   * @param token
   * @param requestURI
   * @param referrer
   * @return
   */
  boolean validate(String token, String requestURI, String referrer);

  /**
   *
   * @return
   */
  boolean isEnabled();
}
