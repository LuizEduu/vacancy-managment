package br.com.luizeduu.vacancy_management.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDto;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateJobUseCase;
import jakarta.validation.Valid;

@RequestMapping("/job")
@RestController
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping
  public ResponseEntity<Job> create(@Valid @RequestBody CreateJobDto createJobDto) {
    var job = this.createJobUseCase.execute(createJobDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(job);
  }
}
