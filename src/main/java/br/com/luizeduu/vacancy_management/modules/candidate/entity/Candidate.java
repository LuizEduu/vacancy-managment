package br.com.luizeduu.vacancy_management.modules.candidate.entity;

import java.util.UUID;

import lombok.Data;

@Data
public class Candidate {
  private UUID id;
  private String name;
  private String username;
  private String email;
  private String password;
  private String description;
  private String curriculum;
}
