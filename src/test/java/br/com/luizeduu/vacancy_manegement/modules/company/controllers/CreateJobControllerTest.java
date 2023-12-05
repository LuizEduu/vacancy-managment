package br.com.luizeduu.vacancy_manegement.modules.company.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.luizeduu.vacancy_management.VacancyManagementApplication;
import br.com.luizeduu.vacancy_management.modules.company.dto.CreateJobDTO;

@SpringBootTest(classes = VacancyManagementApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration()
public class CreateJobControllerTest {
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var createJobDTO = CreateJobDTO.builder()
        .benefits("nenhum")
        .compensation(500F)
        .description("vaga de escravidão")
        .level("estagiario")
        .build();

    var result = mockMvc.perform(post("/company")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectToJson(createJobDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());

    System.out.println(result);
  }

  private static String objectToJson(Object obj) {
    try {
      final ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
