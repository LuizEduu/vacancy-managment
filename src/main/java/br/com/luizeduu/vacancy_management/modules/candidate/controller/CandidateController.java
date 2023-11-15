package br.com.luizeduu.vacancy_management.modules.candidate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @PostMapping
  public ResponseEntity<Candidate> create(@Valid @RequestBody CreateCandidateDTO candidateDTO) {
    var candidate = new Candidate();
    return ResponseEntity.ok(candidate);
  }
}
