package br.com.luizeduu.vacancy_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RequestMapping("/company")
@RestController
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping
  public ResponseEntity<Company> create(@Valid @RequestBody CreateCompanyDTO createCompanyDto) {
    var company = this.createCompanyUseCase.execute(createCompanyDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(company);
  }
}
