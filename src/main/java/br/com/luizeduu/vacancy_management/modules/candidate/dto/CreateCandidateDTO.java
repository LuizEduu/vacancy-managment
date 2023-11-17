package br.com.luizeduu.vacancy_management.modules.candidate.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCandidateDTO {
  @NotBlank()
  private String name;

  @NotBlank()
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
  private String username;

  @NotNull()
  @Email(message = "O campo [email] deve conter um e-mail válido")
  private String email;

  @NotBlank()
  @NotNull()
  @Length(min = 10, max = 100, message = "O comprimento deve ser entre 10 e 100")
  private String password;

  private String description;
}
