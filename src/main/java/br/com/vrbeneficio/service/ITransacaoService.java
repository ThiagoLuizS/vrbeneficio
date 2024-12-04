package br.com.vrbeneficio.service;

import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.TransacaoForm;

public interface ITransacaoService {
    Cartao transacao(TransacaoForm transacaoForm);
    void validarCartao(Cartao cartao, TransacaoForm transacaoForm);
}
