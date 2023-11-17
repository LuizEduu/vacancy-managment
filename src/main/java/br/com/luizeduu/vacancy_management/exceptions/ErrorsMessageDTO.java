package br.com.luizeduu.vacancy_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorsMessageDTO {
  private String message;
  private String field;

}
