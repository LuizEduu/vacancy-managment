package br.com.luizeduu.vacancy_management.provider;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;

@Service
public class JWTCompanyProvider {

  @Value("${security.token.secret_company}")
  private String companySecretKey;

  public DecodedJWT validateToken(String token) throws JWTVerificationException {
    token = token.replace("Bearer ", "");

    return JWT.require(Algorithm.HMAC256(companySecretKey)).build().verify(token);
  }

  public AuthCompanyResponseDTO generateToken(String subject) {
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(expiresIn)
        .withSubject(subject)
        .withClaim("roles", Arrays.asList("COMPANY"))
        .withClaim("grant_type", "company")
        .sign(Algorithm.HMAC256(companySecretKey));

    return AuthCompanyResponseDTO.builder()
        .access_token(token)
        .expiresIn(expiresIn.toEpochMilli()).build();
  }
}
