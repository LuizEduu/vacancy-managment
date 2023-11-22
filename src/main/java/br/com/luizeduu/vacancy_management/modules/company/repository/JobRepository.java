package br.com.luizeduu.vacancy_management.modules.company.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luizeduu.vacancy_management.modules.company.entity.Job;

public interface JobRepository extends JpaRepository<Job, UUID> {
  List<Job> findByDescriptionContaining(String description);
}
