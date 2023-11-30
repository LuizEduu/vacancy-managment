package br.com.luizeduu.vacancy_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJobDTO {

  @Schema(example = "Vaga para pessoa desenvolvedora", requiredMode = RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "Gympass, VA, VR, plano de saúde", requiredMode = RequiredMode.REQUIRED)
  @NotBlank(message = "Esse campo é obrigatorio")
  private String benefits;

  @Schema(example = "Junior", requiredMode = RequiredMode.REQUIRED)
  @NotBlank(message = "Esse campo é obrigatorio")
  private String level;

  @Schema(example = "2500", requiredMode = RequiredMode.REQUIRED)
  private Float compensation;
}
