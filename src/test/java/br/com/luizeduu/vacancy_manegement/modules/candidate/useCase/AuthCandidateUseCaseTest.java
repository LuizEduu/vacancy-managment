package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.luizeduu.vacancy_management.exceptions.AuthNotFoundException;
import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.AuthCandidateUseCase;
import br.com.luizeduu.vacancy_management.provider.JWTCandidateProvider;
import br.com.luizeduu.vacancy_management.provider.dto.CandidateTokenResponseDTO;

@ExtendWith(MockitoExtension.class)
public class AuthCandidateUseCaseTest {
  @InjectMocks
  private AuthCandidateUseCase authCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JWTCandidateProvider jwtCandidateProvider;

  @Test
  @DisplayName("shoud not be able to auth a candidate with candidate not found")
  public void shoud_not_be_able_to_auth_a_candidate_with_candidate_not_found() {

    var authCandidateDTO = new AuthCandidateDTO("any_username", "any_password");

    assertThrows(AuthNotFoundException.class, () -> {
      authCandidateUseCase.execute(authCandidateDTO);
    });
    verify(candidateRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(0)).matches(anyString(), anyString());
  }

  @Test
  @DisplayName("shoud not be able to auth candidate with password not matches")
  public void shoud_not_be_able_to_auth_a_candidate_with_password_not_matches() {
    var authCandidateDTO = new AuthCandidateDTO("any_username", "any_password");

    var candidate = Candidate.builder()
        .id(UUID.randomUUID())
        .password("not_matched_password")
        .build();

    when(candidateRepository.findByUsername("any_username")).thenReturn(Optional.of(candidate));
    when(passwordEncoder.matches("any_password", "not_matched_password")).thenReturn(false);

    assertThrows(AuthNotFoundException.class, () -> {
      authCandidateUseCase.execute(authCandidateDTO);
    });
    verify(candidateRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(1)).matches("any_password", "not_matched_password");
  }

  @Test
  @DisplayName("shoud be able to auth candidate with username and password is valid")
  public void shoud_be_able_to_auth_candidate_with_username_and_password_is_valid() {
    var authCandidateDTO = new AuthCandidateDTO("any_username", "any_password");

    var candidate = Candidate.builder()
        .id(UUID.randomUUID())
        .password("any_password")
        .build();

    when(candidateRepository.findByUsername("any_username")).thenReturn(Optional.of(candidate));
    when(passwordEncoder.matches("any_password", "any_password")).thenReturn(true);

    var now = Instant.now().toEpochMilli();

    var candidateTokenResponseDto = CandidateTokenResponseDTO.builder()
        .token("any_token")
        .expiresIn(now).build();

    when(jwtCandidateProvider.generateToken(candidate.getId().toString())).thenReturn(candidateTokenResponseDto);

    var result = authCandidateUseCase.execute(authCandidateDTO);

    var expectedAuthResult = AuthCandidateResponseDTO.builder()
        .acess_token("any_token")
        .expires_in(now)
        .build();

    assertEquals(result, expectedAuthResult);
    verify(candidateRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(1)).matches("any_password", "any_password");
    verify(jwtCandidateProvider, times(1)).generateToken(candidate.getId().toString());
  }
}
