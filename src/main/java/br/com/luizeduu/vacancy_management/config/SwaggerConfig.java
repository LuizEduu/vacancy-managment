package br.com.luizeduu.vacancy_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/*
 *  spring faz o scan das classes no start e mapear as configurações e aplicar as mesmas
 */
@Configuration
public class SwaggerConfig {

  @Bean // sobrescrever implementação existente
  OpenAPI openAPI() {
    return new OpenAPI().info(new Info().title("vacancy management")
        .description("aplicação responsável por fazer gestão de vagas e candidatos").version("1"))
        .schemaRequirement("JWT_auth", createSecurityScheme());
  }

  private SecurityScheme createSecurityScheme() {
    return new SecurityScheme().name("JWT_auth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
  }
}
