package br.com.luizeduu.vacancy_management.exceptions;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
    super("Empresa não encontrada.");
  }
}
