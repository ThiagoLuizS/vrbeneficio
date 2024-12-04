package br.com.vrbeneficio.service;

import br.com.vrbeneficio.exception.ValidarCartaoException;
import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.TransacaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import br.com.vrbeneficio.models.enums.MensagemCartaoEnum;
import br.com.vrbeneficio.repository.CartaoRepository;
import br.com.vrbeneficio.service.impl.CartaoServiceImpl;
import br.com.vrbeneficio.service.impl.TransacaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceImplTest {

    @Mock
    private TransacaoServiceImpl transacaoService;

    @Test
    public void whenAssetSave_thenTransacaoFlowAsExpected_SALDO_INSUFICIENTE() {
        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456789")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").saldo(BigDecimal.valueOf(50)).build();

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.SALDO_INSUFICIENTE.name()))
                .when(transacaoService).validarCartao(cartao, transacao);

        ValidarCartaoException exception = assertThrows(ValidarCartaoException.class, () -> {
            transacaoService.validarCartao(cartao, transacao);
        });

        assertEquals(MensagemCartaoEnum.SALDO_INSUFICIENTE.name(), exception.getMessage());

        Mockito.verify(transacaoService).validarCartao(cartao, transacao);
    }

    @Test
    public void whenAssetSave_thenTransacaoFlowAsExpected_SENHA_INVALIDA() {
        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(12345678L).senha("123456789").saldo(BigDecimal.valueOf(50)).build();

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.SENHA_INVALIDA.name()))
                .when(transacaoService).validarCartao(cartao, transacao);

        ValidarCartaoException exception = assertThrows(ValidarCartaoException.class, () -> {
            transacaoService.validarCartao(cartao, transacao);
        });

        assertEquals(MensagemCartaoEnum.SENHA_INVALIDA.name(), exception.getMessage());

        Mockito.verify(transacaoService).validarCartao(cartao, transacao);
    }

    @Test
    public void whenAssetSave_thenTransacaoFlowAsExpected_CARTAO_INEXISTENTE() {
        TransacaoForm transacao = TransacaoForm.builder().numeroCartao(12345678L).senha("123456")
                .valor(BigDecimal.valueOf(100)).build();

        Cartao cartao = Cartao.builder().numeroCartao(123456L).senha("123456789").saldo(BigDecimal.valueOf(50)).build();

        Mockito.doThrow(new ValidarCartaoException(MensagemCartaoEnum.CARTAO_INEXISTENTE.name()))
                .when(transacaoService).validarCartao(cartao, transacao);

        ValidarCartaoException exception = assertThrows(ValidarCartaoException.class, () -> {
            transacaoService.validarCartao(cartao, transacao);
        });

        assertEquals(MensagemCartaoEnum.CARTAO_INEXISTENTE.name(), exception.getMessage());

        Mockito.verify(transacaoService).validarCartao(cartao, transacao);
    }
}
