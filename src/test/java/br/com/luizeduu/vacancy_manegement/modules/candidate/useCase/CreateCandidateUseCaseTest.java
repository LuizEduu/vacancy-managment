package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.luizeduu.vacancy_management.exceptions.UserFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.CreateCandidateUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

  @InjectMocks
  private CreateCandidateUseCase createCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("Shoud not be able to create a new candidate with candidate is already exists")
  public void shoud_not_be_able_to_create_a_new_candidate_with_username_is_already_exists() {

    var candidateDto = new CreateCandidateDTO();
    candidateDto.setUsername("username");
    candidateDto.setDescription("any_description");
    candidateDto.setEmail("validemail@test.com");
    candidateDto.setName("any_name");
    candidateDto.setPassword("any_valid_password");

    var candidate = Candidate.builder()
        .id(UUID.randomUUID())
        .build();

    when(candidateRepository.findByUsernameOrEmail("username", "validemail@test.com"))
        .thenReturn(Optional.of(candidate));

    assertThrows(UserFoundException.class, () -> {
      createCandidateUseCase.execute(candidateDto);
    });

    verify(passwordEncoder, times(0)).encode(candidateDto.getPassword());
    verify(candidateRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Shoud be able to create a new candidate")
  public void shoud_be_able_to_create_a_new_candidate() {
    var createdCandidate = Candidate.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .name("any_name")
        .username("any_username")
        .description("any_description")
        .email("any_email")
        .build();

    var candidate = Candidate.builder()
        .name("any_name")
        .username("any_username")
        .description("any_description")
        .email("any_email")
        .password(passwordEncoder.encode("any_valid_password"))
        .build();

    when(candidateRepository.save(candidate)).thenReturn(createdCandidate);

    var candidateDto = new CreateCandidateDTO();
    candidateDto.setUsername("username");
    candidateDto.setDescription("any_description");
    candidateDto.setEmail("validemail@test.com");
    candidateDto.setName("any_name");
    candidateDto.setPassword("any_valid_password");

    var result = createCandidateUseCase.execute(candidateDto);

    assertEquals(createdCandidate, result);
  }
}
