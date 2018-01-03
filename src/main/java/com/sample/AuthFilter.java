package com.sample;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@Log4j2
@Component
public class AuthFilter extends OncePerRequestFilter {

  private static final String TOKEN_HEADER_NAME = "X-Auth-Token";
  private static final String API_KEY_HEADER_NAME = "X-Api-Key";


  private final AuthValidatorService tokenValidator;

  public AuthFilter(final AuthValidatorService tokenValidator) {
    this.tokenValidator = tokenValidator;
  }

  /**
   *
   * @param httpServletRequest
   * @param httpServletResponse
   * @param filterChain
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

    if (CorsUtils.isPreFlightRequest(httpServletRequest)) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    if (!this.tokenValidator.isEnabled()) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    final String token = httpServletRequest.getHeader(TOKEN_HEADER_NAME);
    String httpServletRequestURI = httpServletRequest.getRequestURI();
    String referrer = httpServletRequest.getHeader(HttpHeaders.REFERER);

    log.warn("httpServletRequestURI: {}", httpServletRequestURI);
    log.warn("Referrer: {}", referrer);

    String errors = this.tokenValidator.validate(token, httpServletRequestURI, referrer);

    if (errors == null) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, errors);
  }
}
