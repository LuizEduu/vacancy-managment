package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.exceptions.JobNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

public class ApplyJobCandidateUseCase {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  public void execute(UUID candidateId, UUID jobId) {
    this.candidateRepository.findById(candidateId).orElseThrow(() -> new CandidateNotFoundException());

    this.jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException());

  }

}
