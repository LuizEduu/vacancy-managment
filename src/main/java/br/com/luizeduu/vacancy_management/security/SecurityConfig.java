package br.com.luizeduu.vacancy_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * configuration indica para o Spring que ele deve ler essa classe e aplicar as configurações que forem definidar no momento que a aplicação startar
 */

@Configuration
public class SecurityConfig {

  /*
   * @Bean indica que o método abaixo vai definir algum objeto já gerenciado pelo
   * spring, nesse caso vai sobrescrever as configurações do securityFilterChain
   * do spring para a nossa configuração
   */

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/candidate").permitAll().requestMatchers("/company").permitAll();
          auth.anyRequest().authenticated();
        });

    return httpSecurity.build();
  }

}
