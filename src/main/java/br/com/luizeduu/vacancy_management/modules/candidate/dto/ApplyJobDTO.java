package br.com.luizeduu.vacancy_management.modules.candidate.dto;

import java.util.UUID;

public record ApplyJobDTO(UUID candidateId, UUID jobId) {
}
