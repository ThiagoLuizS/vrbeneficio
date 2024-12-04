package br.com.vrbeneficio.controller;

import br.com.vrbeneficio.VrbeneficioApplication;
import br.com.vrbeneficio.exception.ValidarCartaoException;
import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.TransacaoForm;
import br.com.vrbeneficio.models.enums.MensagemCartaoEnum;
import br.com.vrbeneficio.repository.CartaoRepository;
import br.com.vrbeneficio.service.impl.CartaoServiceImpl;
import br.com.vrbeneficio.service.impl.TransacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = VrbeneficioApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.yml")
public class TransacaoControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    @MockitoBean
    private TransacaoServiceImpl transacaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Value("${authentication.basic.user}")
    private String user;
    @Value("${authentication.basic.password}")
    private String password;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenTransacao_whenPostTransacao_thenStatus201() throws Exception {

        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456789")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").build();


        Mockito.when(cartaoService.findByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);
        Mockito.when(transacaoService.transacao(transacao)).thenReturn(cartao);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transacoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        Mockito.verify(transacaoService).transacao(transacao);

    }

    @Test
    public void whenPostTransacao_thenException_CARTAO_INEXISTENTE() throws Exception {

        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456789")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").build();

        Mockito.when(cartaoService.findByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.CARTAO_INEXISTENTE.name()))
                .when(transacaoService).transacao(transacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transacoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$").value(MensagemCartaoEnum.CARTAO_INEXISTENTE.name()));

        Mockito.verify(transacaoService).transacao(transacao);

    }

    @Test
    public void whenPostTransacao_thenException_SALDO_INSUFICIENTE() throws Exception {

        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456789")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").build();

        Mockito.when(cartaoService.findByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.SALDO_INSUFICIENTE.name()))
                .when(transacaoService).transacao(transacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transacoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$").value(MensagemCartaoEnum.SALDO_INSUFICIENTE.name()));

        Mockito.verify(transacaoService).transacao(transacao);

    }

    @Test
    public void whenPostTransacao_thenException_SENHA_INVALIDA() throws Exception {

        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456789")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").build();

        Mockito.when(cartaoService.findByNumeroCartao(transacao.getNumeroCartao())).thenReturn(cartao);

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.SENHA_INVALIDA.name()))
                .when(transacaoService).transacao(transacao);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transacoes")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(user, password))
                        .content(asJsonString(transacao))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(jsonPath("$").value(MensagemCartaoEnum.SENHA_INVALIDA.name()));

        Mockito.verify(transacaoService).transacao(transacao);

    }
}
