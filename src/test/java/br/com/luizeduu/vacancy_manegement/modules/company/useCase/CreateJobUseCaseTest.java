package br.com.luizeduu.vacancy_manegement.modules.company.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import br.com.luizeduu.vacancy_management.exceptions.CompanyNotFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateJobUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateJobUseCaseTest {

  @InjectMocks
  private CreateJobUseCase createJobUseCase;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private JobRepository jobRepository;

  @Test
  @DisplayName("shoud not be able to create a new job with company not found")
  public void should_not_be_able_to_create_a_new_job_with_company_not_found() {

    var createJobDTO = CreateJobDTO.builder()
        .benefits("any_benefits")
        .compensation(2000F)
        .description("any_description")
        .level("any_level")
        .build();

    var companyId = UUID.randomUUID();

    assertThrows(CompanyNotFoundException.class, () -> {
      createJobUseCase.execute(createJobDTO, companyId);
    });
    verify(companyRepository, times(1)).findById(eq(companyId));
    verify(jobRepository, times(0)).save(any(Job.class));
  }

  @Test
  @DisplayName("shoud be able to create a new job")
  public void shoud_be_able_to_create_a_new_job() {
    var companyId = UUID.randomUUID();

    var company = Company.builder()
        .id(companyId)
        .build();

    when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

    var job = Job.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .benefits("any_benefits")
        .compensation(2000F)
        .description("any_description")
        .level("any_level")
        .build();

    when(jobRepository.save(any(Job.class))).thenReturn(job);

    var createJobDTO = CreateJobDTO.builder()
        .benefits("any_benefits")
        .compensation(2000F)
        .description("any_description")
        .level("any_level")
        .build();

    var result = createJobUseCase.execute(createJobDTO, companyId);

    assertEquals(result, job);
    verify(companyRepository, times(1)).findById(eq(companyId));
    verify(jobRepository, times(1)).save(any(Job.class));
  }
}
