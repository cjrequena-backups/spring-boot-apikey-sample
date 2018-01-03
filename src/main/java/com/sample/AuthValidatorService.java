package com.sample;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

/**
 *
 */
@Data
@Log4j2
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class AuthValidatorService {

  private TokenValidatorBase tokenValidator;

  @Value("${msg.token.required:Unauthorized}")
  private String tokenRequiredError;

  @Value("${msg.token.invalid:Full authentication is required to access this resource (401.400)}")
  private String tokenInvalidError;

  @Value("${msg.token.exception:Full authentication is required to access this resource (401.500)}")
  private String tokenUnexpectedError;

  /**
   *
   */
  public AuthValidatorService() {
    // default constructor
  }

  /**
   *
   * @return
   */
  public boolean isEnabled() {
    return this.tokenValidator == null ? false : this.tokenValidator.isEnabled();
  }

  /**
   *
   * @param token
   * @param requestURI
   * @param referrer
   * @return
   */
  public String validate(final String token, String requestURI, String referrer) {

    if (this.isEnabled()) {
      log.debug("[AuthValidatorService] Enabled");

      if (token == null || token.equals("")) {
        log.warn("[AuthValidatorService] Missing Token HTTP Header");
        return this.tokenRequiredError;
      }

      try {
        if (!tokenValidator.validate(token, requestURI, referrer)) {
          log.warn("[AuthValidatorService] Invalid Token: {}", new Object[] { token });
          return this.tokenInvalidError;
        }
      } catch (Exception macaroonException) {
        log.error("[AuthValidatorService] Token Error ({}): {}",
          new Object[] { token, macaroonException.getMessage() });

        return this.tokenUnexpectedError;
      }
    }

    return null;
  }

}
