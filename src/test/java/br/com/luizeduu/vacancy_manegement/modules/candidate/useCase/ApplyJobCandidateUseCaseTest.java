package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import br.com.luizeduu.vacancy_management.exceptions.JobNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ApplyJobDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.ApplyJob;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.ApplyJobRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ApplyJobCandidateUseCase;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("Shoud not be able to apply job with candidate not found")
  public void should_not_be_able_apply_job_with_candidate_not_found() {

    var id = UUID.randomUUID();

    assertThrows(CandidateNotFoundException.class, () -> {
      applyJobCandidateUseCase.execute(new ApplyJobDTO(null, id));
    });
    verify(candidateRepository, times(1)).findById(any());
    verify(candidateRepository, times(1)).findById(eq(null));
    verify(jobRepository, times(0)).findById(any());
    verify(applyJobRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should not be able to apply job with job not found")
  public void shoud_not_be_able_apply_job_with_job_not_found() {
    var candidateId = UUID.randomUUID();

    var candidate = Candidate.builder()
        .id(candidateId)
        .build();

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

    assertThrows(JobNotFoundException.class, () -> {
      applyJobCandidateUseCase.execute(new ApplyJobDTO(candidateId, null));
    });

    verify(candidateRepository, times(1)).findById(candidateId);
    verify(candidateRepository, times(1)).findById(eq(candidateId));
    verify(jobRepository, times(1)).findById(any());
    verify(jobRepository, times(1)).findById(null);
    verify(applyJobRepository, times(0)).save(any());
  }

  @Test
  @DisplayName("Should be able to to apply a job")
  public void should_be_able_to_apply_a_job() {
    var candidateId = UUID.randomUUID();
    var jobId = UUID.randomUUID();

    var candidate = Candidate.builder()
        .id(candidateId)
        .build();

    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

    var job = Job.builder()
        .id(jobId)
        .build();

    when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));

    var applyJob = ApplyJob.builder()
        .candidateId(candidateId)
        .jobId(jobId)
        .build();

    var createdApplyJob = ApplyJob.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .job(job)
        .candidate(candidate)
        .build();

    when(applyJobRepository.save(applyJob)).thenReturn(createdApplyJob);

    var returnerdApplyJob = applyJobCandidateUseCase.execute(new ApplyJobDTO(candidateId, jobId));

    assertEquals(createdApplyJob, returnerdApplyJob);
    assertNotNull(returnerdApplyJob.getId());
    verify(candidateRepository, times(1)).findById(eq(candidateId));
    verify(jobRepository, times(1)).findById(eq(jobId));

  }
}
