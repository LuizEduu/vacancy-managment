package br.com.luizeduu.vacancy_management.modules.candidate.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "candidate")
@Valid
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @NotNull()
  @Column(unique = true)
  private String username;

  @Email()
  @NotNull()
  @Column(unique = true)
  private String email;

  @NotNull()
  private String password;

  private String description;

  private String curriculum;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Candidate(String name, String username, String email, String password, String description) {
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.description = description;
  }

}
