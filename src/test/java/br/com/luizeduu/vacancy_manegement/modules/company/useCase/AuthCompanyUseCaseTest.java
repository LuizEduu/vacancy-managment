package br.com.luizeduu.vacancy_manegement.modules.company.useCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.luizeduu.vacancy_management.exceptions.AuthNotFoundException;
import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.repository.CompanyRepository;
import br.com.luizeduu.vacancy_management.modules.company.useCase.AuthCompanyUseCase;
import br.com.luizeduu.vacancy_management.provider.JWTCompanyProvider;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AuthCompanyUseCaseTest {

  @InjectMocks
  private AuthCompanyUseCase authCompanyUseCase;

  @Mock
  private CompanyRepository companyRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JWTCompanyProvider jwtCompanyProvider;

  @Test
  @DisplayName("shoud not be able to authenticate company if company not found")
  public void shoud_not_be_able_to_authenticate_company_if_company_not_found() {
    var authCompanyDTO = new AuthCompanyDTO("any_username", "any_password");

    assertThrows(AuthNotFoundException.class, () -> {
      authCompanyUseCase.execute(authCompanyDTO);
    });

    verify(companyRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(0)).matches(anyString(), anyString());
    verify(jwtCompanyProvider, times(0)).generateToken(anyString());
  }

  @Test
  @DisplayName("shoud not be able to authenticate company if password not matches")
  public void shoud_not_be_able_to_authenticate_company_if_password_not_matches() {
    var authCompanyDTO = new AuthCompanyDTO("any_username", "any_password");

    var company = Company.builder()
        .id(UUID.randomUUID())
        .password("not_matched_password")
        .build();

    when(companyRepository.findByUsername("any_username")).thenReturn(Optional.of(company));
    when(passwordEncoder.matches("any_password", "not_matched_password")).thenReturn(false);

    assertThrows(AuthNotFoundException.class, () -> {
      authCompanyUseCase.execute(authCompanyDTO);
    });

    verify(companyRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(1)).matches("any_password", "not_matched_password");
    verify(jwtCompanyProvider, times(0)).generateToken(anyString());
  }

  @Test
  @DisplayName("shout not be able to authenticate company if jwtProvider throws error")
  public void shoud_not_be_able_to_authenticate_company_if_jwtProvider_throws_error() {
    var authCompanyDTO = new AuthCompanyDTO("any_username", "any_password");

    var id = UUID.randomUUID();

    var company = Company.builder()
        .id(id)
        .password("any_matched_encode_password")
        .build();

    when(companyRepository.findByUsername("any_username")).thenReturn(Optional.of(company));
    when(passwordEncoder.matches("any_password", "any_matched_encode_password")).thenReturn(true);
    when(jwtCompanyProvider.generateToken(id.toString())).thenThrow(new RuntimeException());

    assertThrows(RuntimeException.class, () -> {
      authCompanyUseCase.execute(authCompanyDTO);
    });
    verify(companyRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(1)).matches("any_password", "any_matched_encode_password");
    verify(jwtCompanyProvider, times(1)).generateToken(eq(company.getId().toString()));
  }

  @Test
  @DisplayName("shout be able to authenticate company if username and password is valid")
  public void shoud_be_able_to_authenticate_company_if_username_and_password_is_valid() {
    var id = UUID.randomUUID();
    var company = Company.builder()
        .id(id)
        .password("any_matched_encode_password")
        .build();

    when(companyRepository.findByUsername("any_username")).thenReturn(Optional.of(company));
    when(passwordEncoder.matches("any_password", "any_matched_encode_password")).thenReturn(true);

    var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
        .access_token("any_access_token")
        .expiresIn(Instant.now().toEpochMilli()).build();

    when(jwtCompanyProvider.generateToken(id.toString())).thenReturn(authCompanyResponseDTO);

    var authCompanyDTO = new AuthCompanyDTO("any_username", "any_password");

    var result = authCompanyUseCase.execute(authCompanyDTO);

    assertEquals(result, authCompanyResponseDTO);
    verify(companyRepository, times(1)).findByUsername(eq("any_username"));
    verify(passwordEncoder, times(1)).matches("any_password", "any_matched_encode_password");
    verify(jwtCompanyProvider, times(1)).generateToken(eq(company.getId().toString()));
  }
}
