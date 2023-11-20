package br.com.luizeduu.vacancy_management.modules.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.AuthCandidateUseCase;

@RequestMapping("/auth")
@RestController
public class AuthCandidateController {

  @Autowired
  private AuthCandidateUseCase authCandidateUseCase;

  @PostMapping("/candidate")
  public ResponseEntity<AuthCandidateResponseDTO> auth(@RequestBody AuthCandidateDTO authCandidateDTO) {
    var response = this.authCandidateUseCase.execute(authCandidateDTO);

    return ResponseEntity.ok(response);
  
  }
}
