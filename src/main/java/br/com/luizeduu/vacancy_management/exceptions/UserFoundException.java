package br.com.luizeduu.vacancy_management.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("Usuário já existente.");
  }
}
