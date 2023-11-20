package br.com.luizeduu.vacancy_management.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.luizeduu.vacancy_management.exceptions.dto.ErrorMessageDTO;
import br.com.luizeduu.vacancy_management.exceptions.dto.ErrorsMessageDTO;

/*
 * indica que a classe vai ser 'global' e fazer os tratamentos dos erros/exceptions da aplicação.
 */
@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource message) {
    this.messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class) // toda exception desse tipo vai cair nesse método
  public ResponseEntity<List<ErrorsMessageDTO>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    List<ErrorsMessageDTO> errors = new ArrayList<>();

    e.getBindingResult().getFieldErrors().forEach(err -> {
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      errors.add(new ErrorsMessageDTO(message, err.getField()));
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserFoundException.class)
  public ResponseEntity<ErrorMessageDTO> userFoundException(UserFoundException e) {
    return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(CompanyFoundException.class)
  public ResponseEntity<ErrorMessageDTO> companyFoundException(CompanyFoundException e) {
    return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(AuthNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> authNotFoundException(AuthNotFoundException e) {
    return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(CompanyNotFoundException.class)
  public ResponseEntity<ErrorMessageDTO> companyNotFoundException(CompanyNotFoundException e) {
    return new ResponseEntity<ErrorMessageDTO>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

}
