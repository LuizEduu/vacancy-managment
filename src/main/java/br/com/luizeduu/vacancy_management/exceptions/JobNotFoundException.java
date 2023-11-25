package br.com.luizeduu.vacancy_management.exceptions;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException() {
    super("Vaga não encontrada.");
  }
}
