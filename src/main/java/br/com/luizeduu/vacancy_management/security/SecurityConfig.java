package br.com.luizeduu.vacancy_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/*
 * configuration indica para o Spring que ele deve ler essa classe e aplicar as configurações que forem definidar no momento que a aplicação startar
 */

/*
  * @Bean indica que o método abaixo vai definir algum objeto já gerenciado pelo
  * spring, nesse caso vai sobrescrever as configurações do securityFilterChain
  * do spring para a nossa configuração
  */

@Configuration
@EnableMethodSecurity // habilitar o uso do preAuthorize, validando as rotas
public class SecurityConfig {

  @Autowired
  private SecurityCompanyFilter securityCompanyFilter;

  @Autowired
  private SecurityCandidateFilter securityCandidateFilter;

  private static final String[] SWAGGER_LIST = {
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger-resources/**"
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/candidate").permitAll().requestMatchers("/company").permitAll()
              .requestMatchers("/auth/company").permitAll()
              .requestMatchers("/auth/candidate").permitAll()
              .requestMatchers(SWAGGER_LIST).permitAll();

          auth.anyRequest().authenticated();
        })
        .addFilterBefore(securityCompanyFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);

    return httpSecurity.build();
  }

}
