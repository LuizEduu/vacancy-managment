package br.com.luizeduu.vacancy_management.modules.candidate.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.exceptions.dto.ErrorMessageDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.CreateCandidateUseCase;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ListAllJobsByFilterUseCase;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ProfileCandidateUseCase;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações de candidato")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @PostMapping
  @Operation(summary = "Criação de candidato", description = "função responsável por criar um novo candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "201", content = {
          @Content(schema = @Schema(implementation = Candidate.class))
      }),
      @ApiResponse(responseCode = "422", content = {
          @Content(schema = @Schema(implementation = ErrorMessageDTO.class))
      })
  })
  public ResponseEntity<Candidate> create(@Valid @RequestBody CreateCandidateDTO candidateDTO) {
    var candidate = this.createCandidateUseCase.execute(candidateDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
  }

  @GetMapping
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "busca o perfil de um candidato", description = "retorna o perfil do candidato de acordo com a sua autenticação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "404", description = "User not found", content = {
          @Content(schema = @Schema(implementation = ErrorMessageDTO.class))
      })
  })
  @SecurityRequirement(name = "JWT_auth")
  public ResponseEntity<ProfileCandidateResponseDTO> find(HttpServletRequest request) {
    var candidateId = request.getAttribute("candidate_id");
    var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

    return ResponseEntity.ok(candidate);
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Retorna uma lista de vagas de acordo com o filtro informado")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = Job.class)))
      }),
  })
  @SecurityRequirement(name = "JWT_auth")
  public ResponseEntity<List<Job>> findJobsByFilter(@RequestParam String filter) {
    var jobs = this.listAllJobsByFilterUseCase.execute(filter);

    return ResponseEntity.ok(jobs);
  }

}
