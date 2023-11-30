package br.com.luizeduu.vacancy_manegement.modules.candidate.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.luizeduu.vacancy_management.modules.candidate.usecase.ListAllJobsByFilterUseCase;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ListAllJobsByFilterUseCaseTest {
  @InjectMocks
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("Shoud be able to list a jobs")
  public void shoud_be_able_to_list_a_jobs() {
    var jobList = new ArrayList<Job>();
    jobList.add(Job.builder().id(UUID.randomUUID()).description("any_description").level("any_level").build());

    when(jobRepository.findByDescriptionContainingIgnoreCase("any_description")).thenReturn(jobList);

    var result = listAllJobsByFilterUseCase.execute("any_description");

    assertEquals(result, jobList);
  }

}
