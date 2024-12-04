package br.com.vrbeneficio.controller;

import br.com.vrbeneficio.models.dto.form.TransacaoForm;
import br.com.vrbeneficio.resource.TransacaoResource;
import br.com.vrbeneficio.service.ITransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transacoes")
public class TransacaoController implements TransacaoResource {

    private final ITransacaoService service;

    @Override
    public ResponseEntity<String> transacao(TransacaoForm transacaoForm) {
        service.transacao(transacaoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.OK.name());
    }
}
