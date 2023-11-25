package br.com.luizeduu.vacancy_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCandidateResponseDTO {

  @Schema(example = "Descrição do candidato")
  private String description;

  @Schema(example = "Luizrocha214612")
  private String username;

  @Schema(example = "luizrocha@example.com.br")
  private String email;

  private UUID id;

  @Schema(example = "Luiz Eduardo")
  private String name;

}
