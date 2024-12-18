package br.com.vrbeneficio.controller;

import br.com.vrbeneficio.exception.NotFoundCustomException;
import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import br.com.vrbeneficio.resource.CartaoResource;
import br.com.vrbeneficio.service.ICartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartoes")
public class CartaoController implements CartaoResource {

    private final ICartaoService service;

    @Override
    public ResponseEntity<CartaoView> salvar(CartaoForm cartaoForm) {
        return service.findByNumeroCartaoToView(cartaoForm.getNumeroCartao())
                .map(view -> ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(view))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(cartaoForm)));
    }

    @Override
    public ResponseEntity<Optional<BigDecimal>> buscarSaldoCartao(Long numeroCartao) {
        return Optional.ofNullable(service.findByNumeroCartao(numeroCartao))
                .map(cartao -> ResponseEntity.ok(Optional.of(cartao.getSaldo())))
                .orElseThrow(() -> new NotFoundCustomException(""));
    }
}
