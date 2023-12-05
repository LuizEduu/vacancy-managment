package br.com.luizeduu.vacancy_manegement.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

  public static String objectToJson(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String generateToken(UUID companyId, String secret) {
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(expiresIn)
        .withSubject(companyId.toString())
        .withClaim("roles", Arrays.asList("COMPANY"))
        .withClaim("grant_type", "company")
        .sign(Algorithm.HMAC256(secret));

    return token;
  }
}
