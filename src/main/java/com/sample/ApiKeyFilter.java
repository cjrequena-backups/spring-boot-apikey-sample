package com.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Log4j2
@Order(Ordered.LOWEST_PRECEDENCE)
public class ApiKeyFilter implements Filter {

  private static final String API_KEY_HEADER_NAME = "X-API-KEY";

  private final ApiKeyValidatorService validator;

  public ApiKeyFilter(final ApiKeyValidatorService validator) {
    this.validator = validator;
  }

  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    // not necessary for now
  }

  @Override
  public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
    throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    log.warn("[ApiKeyFilter] doFilter()");

    if (CorsUtils.isPreFlightRequest(request)) {
      chain.doFilter(request, response);
      return;
    }

    if (!this.validator.isEnabled()) {
      log.warn("[ApiKeyFilter] API Key is disabled");
      chain.doFilter(request, response);
      return;
    }

    final String apiKey = request.getHeader(API_KEY_HEADER_NAME);
    String requestURI = request.getRequestURI();
    String referrer = request.getHeader(HttpHeaders.REFERER);

    log.warn("requestURI: {}", requestURI);
    log.warn("Referrer: {}", referrer);

    String apiKeyError = this.validator.validateRequestApiKey(apiKey, requestURI, referrer);

    if (apiKeyError == null) {
      log.debug("[ApiKeyFilter] API Key is valid");
      chain.doFilter(request, response);
      return;
    }

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, apiKeyError);
  }

  @Override
  public void destroy() {
    // not necessary for now
  }
}
