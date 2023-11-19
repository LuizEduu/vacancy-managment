package br.com.luizeduu.vacancy_management.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
