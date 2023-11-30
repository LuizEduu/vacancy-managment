package br.com.luizeduu.vacancy_manegement.modules.company.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.luizeduu.vacancy_management.exceptions.CompanyFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateCompanyUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateCompanyUseCaseTest {

  @InjectMocks
  private CreateCompanyUseCase createCompanyUseCase;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("shoud not be able to create a new company if compay is exists")
  public void shoud_not_be_able_to_create_a_new_company_if_company_is_exists() {
    var companyDTO = CreateCompanyDTO.builder()
        .description("any_description")
        .email("any_validemail@test.com")
        .name("any_name")
        .password("any_valid_password")
        .username("any_valid_username")
        .build();

    var company = Company.builder()
        .createdAt(LocalDateTime.now())
        .id(UUID.randomUUID())
        .build();

    when(companyRepository.findByUsernameOrEmail(companyDTO.getUsername(), companyDTO.getEmail()))
        .thenReturn(Optional.of(company));

    assertThrows(CompanyFoundException.class, () -> {
      createCompanyUseCase.execute(companyDTO);
    });
    verify(companyRepository, times(1)).findByUsernameOrEmail(companyDTO.getUsername(), companyDTO.getEmail());
    verify(passwordEncoder, times(0)).encode("any_valid_password");
    verify(companyRepository, times(0)).save(any(Company.class));
  }

  @Test
  @DisplayName("shoud be able to create a new company")
  public void should_be_able_to_create_a_new_company() {
    var company = Company.builder()
        .id(UUID.randomUUID())
        .createdAt(LocalDateTime.now())
        .description("any_description")
        .email("any_valid_email@test.com.br")
        .name("any_name")
        .username("any_username")
        .build();

    when(companyRepository.save(any(Company.class))).thenReturn(company);

    var companyDTO = CreateCompanyDTO.builder()
        .description("any_description")
        .email("any_validemail@test.com")
        .name("any_name")
        .password("any_valid_password")
        .username("any_valid_username")
        .build();

    var result = createCompanyUseCase.execute(companyDTO);

    assertEquals(result, company);
    verify(companyRepository, times(1)).findByUsernameOrEmail(companyDTO.getUsername(), companyDTO.getEmail());
    verify(passwordEncoder, times(1)).encode(companyDTO.getPassword());
    verify(companyRepository, times(1)).save(any(Company.class));

  }

}
