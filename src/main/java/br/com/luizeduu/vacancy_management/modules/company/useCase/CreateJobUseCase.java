package br.com.luizeduu.vacancy_management.modules.company.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDto;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public Job execute(CreateJobDto createJobDto) {

    var job = new Job(createJobDto.getDescription(), createJobDto.getBenefits(), createJobDto.getLevel(),
        createJobDto.getCompanyId());

    return this.jobRepository.save(job);
  }
}
