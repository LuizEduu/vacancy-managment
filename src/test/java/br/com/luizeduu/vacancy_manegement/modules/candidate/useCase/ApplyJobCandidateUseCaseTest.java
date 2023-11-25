package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ApplyJobCandidateUseCase;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Shoud not be able to apply job with candidate not found")
  public void should_not_be_able_apply_job_with_candidate_not_found() {

    var id = UUID.randomUUID();

    assertThrows(CandidateNotFoundException.class, () -> {
      this.applyJobCandidateUseCase.execute(null, id);
    });

    verify(candidateRepository, times(1)).findById(any());
    verify(candidateRepository, times(1)).findById(eq(null));

  }
}
