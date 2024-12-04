package br.com.vrbeneficio.controller;

import br.com.vrbeneficio.VrbeneficioApplication;
import br.com.vrbeneficio.exception.NotFoundCustomException;
import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import br.com.vrbeneficio.models.mapper.CartaoMapper;
import br.com.vrbeneficio.repository.CartaoRepository;
import br.com.vrbeneficio.service.impl.CartaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VrbeneficioApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yml")
public class CartaoControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @MockitoBean
    private CartaoController cartaoController;

    @Mock
    private CartaoMapper cartaoMapper;

    @Value("${authentication.basic.user}")
    private String user;
    @Value("${authentication.basic.password}")
    private String password;

    @Test
    public void whenPostCartao_thenStatusCreated() throws Exception {

        CartaoView cartaoView = CartaoView.builder().numeroCartao(12345678L).senha("123456789").build();

        CartaoForm cartaoForm = CartaoForm.builder().numeroCartao(12345678L).senha("123456789").build();

        Mockito.when(cartaoService.salvar(cartaoForm)).thenReturn(cartaoView);
        Mockito.when(cartaoController.salvar(cartaoForm)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(cartaoView));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cartoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(cartaoForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void whenGetCartao_thenStatusOk() throws Exception {

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789")
                .saldo(BigDecimal.valueOf(500)).build();

        Mockito.when(cartaoService.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);
        Mockito.when(cartaoController.buscarSaldoCartao(cartao.getNumeroCartao())).thenReturn(ResponseEntity.ok(Optional.of(BigDecimal.valueOf(500))));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cartoes/{numeroCartao}", cartao.getNumeroCartao())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Mockito.verify(cartaoController).buscarSaldoCartao(cartao.getNumeroCartao());
    }

    @Test
    public void whenGetCartao_thenStatusNotFoundException() throws Exception {

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789")
                .saldo(BigDecimal.valueOf(500)).build();

        Mockito.when(cartaoService.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);

        Mockito.doThrow(new NotFoundCustomException(""))
                .when(cartaoController).buscarSaldoCartao(cartao.getNumeroCartao());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/cartoes/{numeroCartao}", cartao.getNumeroCartao())
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(""));

        Mockito.verify(cartaoController).buscarSaldoCartao(cartao.getNumeroCartao());
    }


}
