package br.com.luizeduu.vacancy_management.exceptions;

public class CandidateNotFoundException extends RuntimeException {
  public CandidateNotFoundException() {
    super("Usuário não encontrado.");
  }
}
