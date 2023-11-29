package br.com.luizeduu.vacancy_management.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CandidateTokenResponseDTO {
  private String token;
  private Long expiresIn;
}
