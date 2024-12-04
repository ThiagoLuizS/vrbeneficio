package br.com.vrbeneficio.service;

import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import br.com.vrbeneficio.models.mapper.CartaoMapper;
import br.com.vrbeneficio.repository.CartaoRepository;
import br.com.vrbeneficio.service.impl.CartaoServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl cartaoService;
    @Mock
    private CartaoRepository  cartaoRepository;
    @Mock
    private CartaoMapper cartaoMapper;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(CartaoServiceImplTest.class);
    }

    @Test
    public void whenAssetSave_thenCartaoFlowAsExpected() {
        CartaoForm cartaoForm = CartaoForm.builder().numeroCartao(123456778L).senha("1234").build();
        Cartao cartao = Cartao.builder().numeroCartao(123456778L).senha("1234").build();

        Mockito.when(cartaoRepository.save(Mockito.any(Cartao.class))).thenReturn(cartao);
        Mockito.when(cartaoMapper.formToEntity(Mockito.any(CartaoForm.class))).thenReturn(cartao);

        cartaoService.salvar(cartaoForm);

        Mockito.verify(cartaoRepository).save(Mockito.any(Cartao.class));
    }

    @Test
    public void whenAssetFind_thenCartaoFlowAsExpected() {
        CartaoForm cartaoForm = CartaoForm.builder().numeroCartao(123456778L).senha("1234").build();
        Cartao cartao = Cartao.builder().numeroCartao(123456778L).senha("1234").build();
        CartaoView cartaoView = CartaoView.builder().numeroCartao(123456778L).senha("1234").build();

        Mockito.when(cartaoRepository.findByNumeroCartao(Mockito.any(Long.class))).thenReturn(cartao);
        Mockito.when(cartaoMapper.entityToView(Mockito.any(Cartao.class))).thenReturn(cartaoView);

        cartaoService.findByNumeroCartaoToView(cartaoForm.getNumeroCartao());

        Mockito.verify(cartaoRepository).findByNumeroCartao(Mockito.any(Long.class));
    }
}
