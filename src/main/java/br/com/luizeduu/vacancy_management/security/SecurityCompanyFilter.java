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

import br.com.luizeduu.vacancy_management.provider.JWTCompanyProvider;
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
public class SecurityCompanyFilter extends OncePerRequestFilter {

  @Autowired
  private JWTCompanyProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var header = request.getHeader("Authorization");

    if ((request.getRequestURI().contains("company") || request.getRequestURI().contains("job")) && header != null) {
      var token = this.validateToken(header);

      if (token == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      /*
       * Spring security trabalha com contexto de auth e necessita saber quais as
       * roles o usuário tem acesso. por meio do Username... eu seto quais roles esse
       * user tem
       * Por meio do securityContextHolder eu seto no contexto do usuário essas
       * permissões
       */

      request.setAttribute("company_id", token.getSubject());

      var roles = token.getClaim("roles").asList(String.class);

      var authGrants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

      var auth = new UsernamePasswordAuthenticationToken(token.getSubject(),
          null, authGrants);

      SecurityContextHolder.getContext().setAuthentication(auth);

    }

    filterChain.doFilter(request, response);
  }

  private DecodedJWT validateToken(String header) {
    try {
      var token = this.jwtProvider.validateToken(header);

      return token;
    } catch (TokenExpiredException e) {
      return null;
    } catch (JWTVerificationException e) {
      return null;
    }
  }

}
