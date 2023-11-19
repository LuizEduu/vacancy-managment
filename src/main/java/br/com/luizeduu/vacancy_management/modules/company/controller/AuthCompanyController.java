package br.com.luizeduu.vacancy_management.modules.company.controller;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.useCase.AuthCompanyUseCase;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthCompanyController {

  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/company")
  public ResponseEntity<String> auth(@Valid @RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var token = this.authCompanyUseCase.execute(authCompanyDTO);

    return ResponseEntity.ok(token);
  }
}
