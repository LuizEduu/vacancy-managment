package br.com.luizeduu.vacancy_management.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateTokenResponseDTO {
  private String token;
  private Long expiresIn;
}
