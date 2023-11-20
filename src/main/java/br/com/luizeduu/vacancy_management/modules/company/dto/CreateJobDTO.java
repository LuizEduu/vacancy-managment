package br.com.luizeduu.vacancy_management.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateJobDTO {
  private String description;

  @NotBlank(message = "Esse campo é obrigatorio")
  private String benefits;

  @NotBlank(message = "Esse campo é obrigatorio")
  private String level;

  private Float compensation;
}
