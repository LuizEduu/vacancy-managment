package br.com.luizeduu.vacancy_management.modules.candidate.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.luizeduu.vacancy_management.exceptions.JobNotFoundException;
import br.com.luizeduu.vacancy_management.modules.candidate.dto.ApplyJobDTO;
import br.com.luizeduu.vacancy_management.modules.candidate.entity.ApplyJob;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.ApplyJobRepository;
import br.com.luizeduu.vacancy_management.modules.candidate.repository.CandidateRepository;
import br.com.luizeduu.vacancy_management.modules.company.repository.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJob execute(ApplyJobDTO applyJobDTO) {
    this.candidateRepository.findById(applyJobDTO.candidateId()).orElseThrow(() -> new CandidateNotFoundException());

    this.jobRepository.findById(applyJobDTO.jobId()).orElseThrow(() -> new JobNotFoundException());

    var applyJob = ApplyJob.builder()
        .candidateId(applyJobDTO.candidateId())
        .jobId(applyJobDTO.jobId())
        .build();

    return applyJobRepository.save(applyJob);

  }

}
