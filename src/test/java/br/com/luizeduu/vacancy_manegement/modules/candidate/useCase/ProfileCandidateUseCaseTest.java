package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ProfileCandidateUseCase;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {

  @InjectMocks
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Test
  @DisplayName("Shoud not be able to return a candidate profile with not found")
  public void shoud_not_be_able_to_return_a_candidate_profile_with_not_found() {
    assertThrows(CandidateNotFoundException.class, () -> {
      profileCandidateUseCase.execute(UUID.randomUUID());
    });
    verify(candidateRepository, times(1)).findById(any(UUID.class));
  }

  @Test
  @DisplayName("shoud be able to return a candidate profile is exists")
  public void shoud_be_able_to_return_a_candidate_profile_is_exists() {
    var candidate = Candidate.builder()
        .createdAt(LocalDateTime.now())
        .curriculum("any_curriculum")
        .description("any_description")
        .email("any_email")
        .id(UUID.randomUUID())
        .name("any_name")
        .build();

    when(candidateRepository.findById(any(UUID.class))).thenReturn(Optional.of(candidate));

    var result = profileCandidateUseCase.execute(UUID.randomUUID());

    assertNotNull(result.getId());
    assertInstanceOf(ProfileCandidateResponseDTO.class, result);
  }

}
