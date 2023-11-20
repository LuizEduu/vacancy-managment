package br.com.luizeduu.vacancy_management.provider;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTProvider {

  @Value("${security.token.secret_company}")
  private String secretKey;

  public String validateToken(String token) throws JWTVerificationException {
    token = token.replace("Bearer ", "");

    var subject = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token)
        .getSubject();

    return subject;
  }

  public String generateToken(String companyId) {
    return JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(companyId)
        .sign(Algorithm.HMAC256(secretKey));
  }
}
