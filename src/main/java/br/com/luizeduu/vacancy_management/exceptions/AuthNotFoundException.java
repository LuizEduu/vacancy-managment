package br.com.luizeduu.vacancy_management.exceptions;

public class AuthNotFoundException extends RuntimeException {

  public AuthNotFoundException() {
    super("Usuário ou senha incorretos.");
  }
}
