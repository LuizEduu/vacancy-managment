package br.com.luizeduu.vacancy_management.modules.company.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateJobDTO {
  private String description;

  @NotBlank(message = "Esse campo é obrigatorio")
  private String benefits;

  @NotBlank(message = "Esse campo é obrigatorio")
  private String level;

  @NotNull
  private UUID companyId;

  private Float compensation;
}
