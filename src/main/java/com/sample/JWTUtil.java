package com.sample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Log4j2
public class JWTUtil {

  /**
   *
   * @param id
   * @param issuer
   * @param secretApiKey
   * @param subject
   * @param expirationMilliseconds
   * @return
   */
  public static String generateToken(String id, String issuer, String subject, String secretApiKey, long expirationMilliseconds) {

    //The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //We will sign our JWT with our ApiKey secret
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretApiKey);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder()
      .setId(id)
      .setIssuedAt(now)
      .setSubject(subject)
      .setIssuer(issuer)
      .signWith(signatureAlgorithm, signingKey);

    //if it has been specified, let's add the expiration
    if (expirationMilliseconds >= 0) {
      long expMillis = nowMillis + expirationMilliseconds;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }

    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }

  /**
   *
   * @param claims
   * @param secretApiKey
   * @param expirationMilliseconds
   * @return
   */
  public static String generateToken(Map header, Map claims, String secretApiKey, long expirationMilliseconds) {

    //The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    //We will sign our JWT with our ApiKey secret
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretApiKey);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder()
      .setHeader(header)
      .setClaims(claims)
      .signWith(signatureAlgorithm, signingKey);

    //if it has been specified, let's add the expiration
    if (expirationMilliseconds >= 0) {
      long expMillis = nowMillis + expirationMilliseconds;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }

    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }

  /**
   *
   * @param secretApiKey
   * @param token
   * @return
   */
  public static Jws<Claims> parseJWTToken(String secretApiKey, String token) {
    return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretApiKey)).parseClaimsJws(token);
  }

  /**
   *
   * @param secretApiKey
   * @param token
   * @return
   */
  public static boolean validateToken(String secretApiKey, String token){
    String signature = parseJWTToken(secretApiKey, token).getSignature();
    JwsHeader header = parseJWTToken(secretApiKey, token).getHeader();
    Claims claims = parseJWTToken(secretApiKey, token).getBody();

    log.info("Signature: {}", signature);
    log.info("Key Id: {}", header.getKeyId());

    log.info("Id: {}", claims.getId());
    log.info("Subject: {}" , claims.getSubject());
    log.info("Issuer: {}" , claims.getIssuer());
    log.info("Expiration: {}" , claims.getExpiration());
    return claims.getSubject()!=null;
  }



}
