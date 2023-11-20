package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.AuthNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.provider.JWTProvider;

@Service
public class AuthCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTProvider jwtProvider;

  public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO) {
    var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
        .orElseThrow(() -> new AuthNotFoundException());

    Boolean passwordMatches = this.passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthNotFoundException();
    }

    var token = this.jwtProvider.generateCandidateToken(candidate.getId().toString());

    return AuthCandidateResponseDTO.builder()
        .acess_token(token).build();

  }
}
