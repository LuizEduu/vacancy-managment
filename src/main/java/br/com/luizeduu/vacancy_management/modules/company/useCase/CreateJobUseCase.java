package br.com.luizeduu.vacancy_management.modules.company.useCase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.CompanyNotFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public Job execute(CreateJobDTO createJobDto, UUID companyId) {
    jobRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException());

    var job = Job.builder()
        .benefits(createJobDto.getBenefits())
        .companyId(companyId)
        .compensation(createJobDto.getCompensation())
        .description(createJobDto.getDescription())
        .level(createJobDto.getLevel())
        .build();

    return this.jobRepository.save(job);
  }
}
