package br.com.luizeduu.vacancy_management.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.luizeduu.vacancy_management.provider.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Extendendo de OncePerRequest dentro do metodo doFilterInternal
 * Eu tenho acesso a todos os dados das requests, seja header, body, params e afins
 * E aqui eu posso configurar comportamentos, filtros para que sejam executados antes da minha request chegar nas camadas de controller
 */

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    /*
     * seta o contexto de permissões do usuário para o
     * spring security ir validando durante o uso
     */
    SecurityContextHolder.getContext().setAuthentication(null);

    var header = request.getHeader("Authorization");

    if (header != null) {
      var subject = this.validateToken(header);

      if (subject.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }

      request.setAttribute("company_id", subject);

      var auth = new UsernamePasswordAuthenticationToken(subject,
          null, Collections.emptyList());

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

  private String validateToken(String token) {
    try {
      var subjectToken = this.jwtProvider.validateToken(token);

      return subjectToken;
    } catch (JWTVerificationException e) {
      return "";
    }
  }

}
