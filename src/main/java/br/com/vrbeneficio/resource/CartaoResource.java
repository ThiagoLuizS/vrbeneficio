package br.com.vrbeneficio.resource;

import br.com.vrbeneficio.models.dto.form.CartaoForm;
import br.com.vrbeneficio.models.dto.view.CartaoView;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface CartaoResource {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<CartaoView> salvar(@RequestBody @Valid CartaoForm cartaoForm);
}
