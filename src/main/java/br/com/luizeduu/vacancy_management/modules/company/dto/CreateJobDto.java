package br.com.luizeduu.vacancy_management.modules.company.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateJobDto {
  private String description;

  private String benefits;

  private String level;

  @NotNull
  private UUID companyId;
}
