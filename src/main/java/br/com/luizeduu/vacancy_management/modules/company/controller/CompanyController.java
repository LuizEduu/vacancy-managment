package br.com.luizeduu.vacancy_management.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.exceptions.dto.ErrorMessageDTO;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateCompanyUseCase;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/job")
  @PreAuthorize("hasRole('COMPANY')")
  @Operation(summary = "Criação de vagas em uma empresa", description = "Essa função é responsável por criar uma nova vaga para uma empresa")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = Job.class))),
      @ApiResponse(responseCode = "422", content = @Content(schema = @Schema(implementation = ErrorMessageDTO.class)))
  })
  @SecurityRequirement(name = "JWT_auth")
  public ResponseEntity<Job> create(@Valid @RequestBody CreateJobDTO createJobDto, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    var job = this.createJobUseCase.execute(createJobDto, UUID.fromString(companyId.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(job);
  }
}
