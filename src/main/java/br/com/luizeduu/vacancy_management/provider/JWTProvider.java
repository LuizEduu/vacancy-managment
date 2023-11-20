package br.com.luizeduu.vacancy_management.provider;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTProvider {

  @Value("${security.token.secret_company}")
  private String companySecretKey;

  @Value("${security.token.secret_candidate}")
  private String candidateSecretKey;

  public String validateCompanyToken(String token) throws JWTVerificationException {
    token = token.replace("Bearer ", "");

    var subject = JWT.require(Algorithm.HMAC256(companySecretKey)).build().verify(token)
        .getSubject();

    return subject;
  }

  public String generateCompanyToken(String subject) {
    return JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(subject)
        .sign(Algorithm.HMAC256(companySecretKey));
  }

  public String validateCandidateToken(String token) throws JWTVerificationException {
    token = token.replace("Bearer ", "");

    var subject = JWT.require(Algorithm.HMAC256(companySecretKey)).build().verify(token)
        .getSubject();

    return subject;
  }

  public String generateCandidateToken(String subject) {
    return JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withClaim("roles", Arrays.asList("candidate"))
        .withSubject(subject)
        .sign(Algorithm.HMAC256(companySecretKey));
  }
}
