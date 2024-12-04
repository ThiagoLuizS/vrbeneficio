package br.com.vrbeneficio.resource;

import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartaoResource {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<CartaoView> salvar(@RequestBody @Valid CartaoForm cartaoForm);

    @GetMapping("/{numeroCartao}")
    ResponseEntity<Optional<BigDecimal>> buscarSaldoCartao(@PathVariable("numeroCartao") Long numeroCartao);
}
