package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID id) {
    var candidate = this.candidateRepository.findById(id).orElseThrow(() -> new CandidateNotFoundException());

    return ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .email(candidate.getEmail())
        .name(candidate.getName())
        .id(candidate.getId())
        .username(candidate.getUsername())
        .build();
  }
}
