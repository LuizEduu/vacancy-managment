package br.com.luizeduu.vacancy_management.modules.company.useCase;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.AuthNotFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;
import br.com.luizeduu.vacancy_management.provider.JWTProvider;

@Service
public class AuthCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTProvider jwtProvider;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
        .orElseThrow(() -> new AuthNotFoundException());

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthNotFoundException();
    }

    return jwtProvider.generateCompanyToken(company.getId().toString());

  }
}
