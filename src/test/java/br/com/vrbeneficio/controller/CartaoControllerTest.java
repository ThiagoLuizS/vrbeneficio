package br.com.vrbeneficio.controller;

import br.com.vrbeneficio.VrbeneficioApplication;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VrbeneficioApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yml")
public class CartaoControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Value("${authentication.basic.user}")
    private String user;
    @Value("${authentication.basic.password}")
    private String password;

    @Test
    public void givenPauta_whenPostPauta_thenStatus201Or422() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/cartoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(CartaoForm.builder().numeroCartao(12345678L).senha("123456789").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 201 || result.getResponse().getStatus() == 422));
    }

}
