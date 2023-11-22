package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

  @Autowired
  private JobRepository jobRepository;

  public List<Job> execute(String filter) {
    return this.jobRepository.findByDescriptionContaining(filter);
  }
}
