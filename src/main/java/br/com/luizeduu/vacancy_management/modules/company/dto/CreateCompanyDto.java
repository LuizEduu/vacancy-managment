package br.com.luizeduu.vacancy_management.modules.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompanyDto {

  @NotBlank
  private String username;

  @Email
  private String email;

  @NotBlank
  @NotNull
  private String password;

  private String website;

  @NotBlank
  private String name;

  private String description;
}
