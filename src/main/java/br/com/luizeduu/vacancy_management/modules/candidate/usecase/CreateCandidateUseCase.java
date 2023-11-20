package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.UserFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.provider.JWTProvider;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JWTProvider jwtProvider;

  public Candidate execute(CreateCandidateDTO candidateDTO) {
    this.candidateRepository.findByUsernameOrEmail(candidateDTO.getUsername(), candidateDTO.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    var candidate = Candidate.builder()
        .name(candidateDTO.getName())
        .username(candidateDTO.getUsername())
        .email(candidateDTO.getEmail())
        .password(jwtProvider.hashPassword(candidateDTO.getPassword()))
        .description(candidateDTO.getDescription()).build();

    this.candidateRepository.save(candidate);

    return candidate;
  }
}
