package br.com.luizeduu.vacancy_management.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    String header = request.getHeader("Authorization");

    if (request.getRequestURI().contains("candidate") && header != null) {
      var token = this.validateToken(header);

      if (token == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      var roles = token.getClaim("roles").asList(String.class);

      /*
       * Spring security por padrão espera que seja ROLE_candidate por exemplo
       */

      request.setAttribute("candidate_id", token.getSubject());

      var authGrants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

      var auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, authGrants);

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
