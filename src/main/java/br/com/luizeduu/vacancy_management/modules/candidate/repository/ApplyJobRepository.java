package br.com.luizeduu.vacancy_management.modules.candidate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luizeduu.vacancy_management.modules.candidate.entity.ApplyJob;

public interface ApplyJobRepository extends JpaRepository<ApplyJob, UUID> {

}
