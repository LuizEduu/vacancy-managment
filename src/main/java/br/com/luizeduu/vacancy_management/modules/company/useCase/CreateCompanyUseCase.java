package br.com.luizeduu.vacancy_management.modules.company.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.CompanyFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDto;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  public Company execute(CreateCompanyDto companyDto) {

    this.companyRepository.findByUsernameOrEmail(companyDto.getUsername(), companyDto.getEmail())
        .ifPresent((company) -> {
          throw new CompanyFoundException();
        });

    var company = new Company(companyDto.getUsername(), companyDto.getEmail(), companyDto.getPassword(),
        companyDto.getWebsite(), companyDto.getName(), companyDto.getDescription());
    return this.companyRepository.save(company);
  }
}
