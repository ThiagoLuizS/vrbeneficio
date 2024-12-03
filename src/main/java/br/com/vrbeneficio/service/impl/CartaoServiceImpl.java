package br.com.vrbeneficio.service.impl;

import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import br.com.vrbeneficio.models.mapper.CartaoMapper;
import br.com.vrbeneficio.models.mapper.MapStructMapper;
import br.com.vrbeneficio.repository.CartaoRepository;
import br.com.vrbeneficio.service.AbstractService;
import br.com.vrbeneficio.service.ICartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaoServiceImpl extends AbstractService<Cartao, CartaoView, CartaoForm> implements ICartaoService {

    private final CartaoRepository cartaoRepository;
    private final CartaoMapper cartaoMapper;

    @Override
    public CartaoView salvar(CartaoForm cartaoForm) {
        return saveToView(cartaoForm);
    }

    @Override
    public Optional<CartaoView> findByNumeroCartao(Long numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
                .map(cartao -> getMapper().entityToView(cartao));
    }

    @Override
    protected MongoRepository<Cartao, String> getRepository() {
        return cartaoRepository;
    }

    @Override
    protected MapStructMapper<Cartao, CartaoView, CartaoForm> getMapper() {
        return cartaoMapper;
    }
}
