package br.com.luizeduu.vacancy_management.modules.company.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.CompanyFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public Company execute(CreateCompanyDTO companyDto) {
    this.companyRepository.findByUsernameOrEmail(companyDto.getUsername(), companyDto.getEmail())
        .ifPresent((company) -> {
          throw new CompanyFoundException();
        });

    var password = this.passwordEncoder.encode(companyDto.getPassword());

    var company = Company.builder()
        .username(companyDto.getUsername())
        .email(companyDto.getEmail())
        .password(password)
        .website(companyDto.getWebsite())
        .name(companyDto.getName())
        .description(companyDto.getDescription()).build();

    return this.companyRepository.save(company);
  }
}
