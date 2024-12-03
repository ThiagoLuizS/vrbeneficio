package br.com.vrbeneficio.service;

import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;

import java.util.Optional;

public interface ICartaoService {
    CartaoView salvar(CartaoForm cartaoForm);
    Optional<CartaoView> findByNumeroCartao(Long numeroCartao);
}
