package br.com.luizeduu.vacancy_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyDTO {

  @NotBlank
  @Schema(example = "companycriacao", requiredMode = RequiredMode.REQUIRED)
  private String username;

  @Email
  @Schema(example = "companyemail@example.com.br", requiredMode = RequiredMode.REQUIRED)
  private String email;

  @NotBlank
  @NotNull
  @Schema(example = "companypassword", requiredMode = RequiredMode.REQUIRED)
  private String password;

  @Schema(example = "companywebsite", requiredMode = RequiredMode.NOT_REQUIRED)
  private String website;

  @NotBlank
  @Schema(example = "company name", requiredMode = RequiredMode.REQUIRED)
  private String name;

  @Schema(example = "company description", requiredMode = RequiredMode.NOT_REQUIRED)
  private String description;
}
