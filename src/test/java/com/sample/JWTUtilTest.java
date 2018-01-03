package com.sample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crequena on 03/05/2016.
 */
@Log4j2
public class JWTUtilTest {


  @Test
  public void generateTokenTest() throws Exception {
    //    String secretKey = UUID.randomUUID().toString();
    //    String apiKey = UUID.randomUUID().toString();
    String secretKey = "b7dc641f-b9ef-4650-afd3-b10d3ec15374";
    String apiKey = "c6f1fb57-40ff-4f58-8301-3cb764c70ace";



    String token = JWTUtil.generateToken("1", "cjrequena", apiKey, secretKey, 10000);
    Claims claims = JWTUtil.parseJWTToken(secretKey, token).getBody();

    log.info("secretKey: {}", secretKey);
    log.info("apiKey: {}", apiKey);
    log.info("token: {}", token);
    log.info("ID: {}", claims.getId());
    log.info("Subject: {}" , claims.getSubject());
    log.info("Issuer: {}" , claims.getIssuer());
    log.info("Expiration: {}" , claims.getExpiration());

    log.info("");
    log.info("=====================");
    log.info("");

    Map claimsMap = new HashMap();
    claimsMap.put("apikey", apiKey);
    claimsMap.put("scope","[admin, read, write]");

    Map headersMap = new HashMap();
    headersMap.put("header1","test");

    token = JWTUtil.generateToken(headersMap, claimsMap, secretKey, 10000);
    claims = JWTUtil.parseJWTToken(secretKey, token).getBody();
    JwsHeader header = JWTUtil.parseJWTToken(secretKey, token).getHeader();

    log.info("ApiKey: {}" , claims.get("apikey"));
    log.info("scope: {}" , claims.get("scope"));
    log.info("header1: {}" , header.get("header1"));
  }


  @Test
  public void parseTokenTest() throws Exception {
    //    String secretKey = UUID.randomUUID().toString();
    //    String apiKey = UUID.randomUUID().toString();
    String secretKey = "b7dc641f-b9ef-4650-afd3-b10d3ec15374";
    String token = JWTUtil.generateToken("1", "cjrequena", "subject", secretKey, 10000);
    Claims claims = JWTUtil.parseJWTToken(secretKey, token).getBody();
    log.info("secretKey: {}", secretKey);
    log.info("token: {}", token);
    log.info("ID: {}", claims.getId());
    log.info("Subject: {}" , claims.getSubject());
    log.info("Issuer: {}" , claims.getIssuer());
    log.info("Expiration: {}" , claims.getExpiration());
  }
}
