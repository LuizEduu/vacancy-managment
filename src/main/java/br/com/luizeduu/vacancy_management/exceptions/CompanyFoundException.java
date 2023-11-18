package br.com.luizeduu.vacancy_management.exceptions;

public class CompanyFoundException extends RuntimeException {
  public CompanyFoundException() {
    super("Empresa já existente.");
  }
}
