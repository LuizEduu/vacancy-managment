package br.com.luizeduu.vacancy_management.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.CreateCandidateUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @PostMapping
  public ResponseEntity<Candidate> create(@Valid @RequestBody CreateCandidateDTO candidateDTO) {
    var candidate = this.createCandidateUseCase.execute(candidateDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(candidate);
  }

}
