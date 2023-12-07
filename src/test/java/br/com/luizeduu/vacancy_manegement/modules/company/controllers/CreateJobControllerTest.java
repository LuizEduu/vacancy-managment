package br.com.luizeduu.vacancy_manegement.modules.company.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.UUID;

import br.com.luizeduu.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.luizeduu.vacancy_management.modules.company.entity.Company;
import br.com.luizeduu.vacancy_management.modules.company.useCase.CreateCompanyUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.luizeduu.vacancy_management.VacancyManagementApplication;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.luizeduu.vacancy_manegement.utils.TestUtils;

@SpringBootTest(classes = VacancyManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration()
@ActiveProfiles("test")
public class CreateJobControllerTest {
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var createCompanyDto = CreateCompanyDTO.builder()
        .description("any_description")
        .email("any_validemail@test.com")
        .name("any_name")
        .password("any_valid_password")
        .username("anyvalidusername")
        .build();

    var company = this.createCompanyUseCase.execute(createCompanyDto);

    var createJobDTO = CreateJobDTO.builder()
        .benefits("VA, VR, Plano de saúde")
        .compensation(500F)
        .description("any_description")
        .level("estagiario")
        .build();

    var result = mockMvc.perform(post("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtils.objectToJson(createJobDTO))
        .header("Authorization", TestUtils.generateToken(company.getId(), "C0mP@nY_S3cr3t_@uTh")))
        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn().getResponse().getContentAsString();

    assertTrue(result.contains(company.getId().toString()));
  }

}
