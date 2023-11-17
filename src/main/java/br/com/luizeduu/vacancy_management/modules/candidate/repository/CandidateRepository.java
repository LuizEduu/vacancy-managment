package br.com.luizeduu.vacancy_management.modules.candidate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.luizeduu.vacancy_management.modules.candidate.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
  Optional<Candidate> findByUsernameOrEmail(String username, String email);
}
