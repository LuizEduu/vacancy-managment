package br.com.luizeduu.vacancy_management.modules.company.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luizeduu.vacancy_management.exceptions.AuthNotFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;
import br.com.luizeduu.vacancy_management.provider.JWTCompanyProvider;

@Service
public class AuthCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTCompanyProvider jwtProvider;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) {
    var company = this.companyRepository.findByUsername(authCompanyDTO.username())
        .orElseThrow(() -> new AuthNotFoundException());

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.password(), company.getPassword());

    if (!passwordMatches) {
      throw new AuthNotFoundException();
    }

    var token = jwtProvider.generateToken(company.getId().toString());

    return AuthCompanyResponseDTO.builder()
        .access_token(token.getAccess_token())
        .expiresIn(token.getExpiresIn())
        .build();

  }
}
