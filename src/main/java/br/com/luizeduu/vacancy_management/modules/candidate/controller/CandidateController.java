package br.com.luizeduu.vacancy_management.modules.candidate.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.CreateCandidateUseCase;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @PostMapping
  public ResponseEntity<Candidate> create(@Valid @RequestBody CreateCandidateDTO candidateDTO) {
    var candidate = this.createCandidateUseCase.execute(candidateDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
  }

  @GetMapping
  @PreAuthorize("hasRole('candidate')")
  public ResponseEntity<ProfileCandidateResponseDTO> find(HttpServletRequest request) {
    var candidateId = request.getAttribute("candidate_id");
    var candidate = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

    return ResponseEntity.ok(candidate);
  }

}
