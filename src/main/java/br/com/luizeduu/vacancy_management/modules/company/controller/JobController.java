package br.com.luizeduu.vacancy_management.modules.company.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Job;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/job")
@RestController
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping
  public ResponseEntity<Job> create(@Valid @RequestBody CreateJobDTO createJobDto, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    var job = this.createJobUseCase.execute(createJobDto, UUID.fromString(companyId.toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(job);
  }
}
