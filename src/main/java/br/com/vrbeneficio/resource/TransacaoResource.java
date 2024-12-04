package br.com.vrbeneficio.resource;

import br.com.vrbeneficio.models.dto.form.TransacaoForm;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TransacaoResource {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<String> transacao(@RequestBody @Valid TransacaoForm transacaoForm);
}
