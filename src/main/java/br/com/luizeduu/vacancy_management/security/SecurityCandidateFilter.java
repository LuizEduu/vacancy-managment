package br.com.luizeduu.vacancy_management.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.luizeduu.vacancy_management.provider.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

  @Autowired
  private JWTCandidateProvider jwtCandidateProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(null);

    String header = request.getHeader("Authorization");

    if (request.getRequestURI().contains("candidate") && header != null) {
      var token = this.validateToken(header);

      if (token == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      request.setAttribute("candidate_id", token.getSubject());

      var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, Collections.emptyList());

      SecurityContextHolder.getContext().setAuthentication(auth);

    }

    filterChain.doFilter(request, response);
  }

  private DecodedJWT validateToken(String header) {
    try {
      var token = this.jwtCandidateProvider.validateToken(header);

      return token;
    } catch (TokenExpiredException e) {
      return null;
    } catch (JWTVerificationException e) {
      return null;
    }
  }

}
