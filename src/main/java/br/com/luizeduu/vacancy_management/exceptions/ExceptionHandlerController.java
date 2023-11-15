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

@ControllerAdvice // indica que a classe vai ser 'global' e fazer os tratamentos dos
                  // erros/exceptions da aplicação.
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource message) {
    this.messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class) // toda exception desse tipo vai cair nesse método
  public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    List<ErrorMessageDTO> errors = new ArrayList<>();

    e.getBindingResult().getFieldErrors().forEach(err -> {
      String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
      errors.add(new ErrorMessageDTO(message, err.getField()));
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
