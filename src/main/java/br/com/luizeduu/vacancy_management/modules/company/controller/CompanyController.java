package br.com.luizeduu.vacancy_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.exceptions.dto.ErrorMessageDTO;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/company")
@RestController
@Tag(name = "Empresas", description = "Operações de empresas")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping
  @Operation(summary = "Cadastro de empresas", description = "Essa função é responsável Cadastrar uma nova empresa")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Company.class))),
      @ApiResponse(responseCode = "422", description = "Company not found", content = @Content(schema = @Schema(implementation = ErrorMessageDTO.class)))
  })
  public ResponseEntity<Company> create(@Valid @RequestBody CreateCompanyDTO createCompanyDto) {
    var company = this.createCompanyUseCase.execute(createCompanyDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(company);
  }
}
