package br.com.luizeduu.vacancy_management.provider;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.luizeduu.vacancy_management.provider.dto.CandidateTokenResponseDTO;

@Service
public class JWTCandidateProvider {
  @Value("${security.token.secret_candidate}")
  private String candidateSecretKey;

  public DecodedJWT validateToken(String token) throws JWTVerificationException, TokenExpiredException {
    token = token.replace("Bearer ", "");

    return JWT.require(Algorithm.HMAC256(candidateSecretKey)).build().verify(token);
  }

  public CandidateTokenResponseDTO generateToken(String subject) {
    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("vacancyManagement")
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("candidate"))
        .withSubject(subject)
        .sign(Algorithm.HMAC256(candidateSecretKey));

    return new CandidateTokenResponseDTO(token, expiresIn.toEpochMilli());
  }
}
