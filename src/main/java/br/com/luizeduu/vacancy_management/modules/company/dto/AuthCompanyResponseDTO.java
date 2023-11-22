package br.com.luizeduu.vacancy_management.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCompanyResponseDTO {
  private String access_token;
  private Long expiresIn;
}
