package br.com.vrbeneficio.service.impl;

import br.com.vrbeneficio.exception.GlobalException;
import br.com.vrbeneficio.exception.ValidarCartaoException;
import br.com.vrbeneficio.models.collection.Cartao;
import br.com.vrbeneficio.models.dto.form.TransacaoForm;
import br.com.vrbeneficio.models.enums.MensagemCartaoEnum;
import br.com.vrbeneficio.service.ICartaoService;
import br.com.vrbeneficio.service.ITransacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements ITransacaoService {

    private final ICartaoService cartaoService;

    @Override
    public Cartao transacao(TransacaoForm transacaoForm) {
        try {
            log.info(">> TRANSAÇÃO [transacaoForm={}]", transacaoForm);
            Cartao cartao = cartaoService.findByNumeroCartao(transacaoForm.getNumeroCartao());

            /*VALIDAR INFORMAÇÕES DO CARTÂO*/
            validarCartao(cartao, transacaoForm);

            /*ABATER SALDO DO CARTÃO*/
            BigDecimal valor = transacaoForm.getValor().abs();
            cartao.setSaldo(cartao.getSaldo().subtract(valor));

            return cartaoService.salvarToEntity(cartao);
        } catch (OptimisticLockingFailureException ex) {
            log.error("<< TRANSAÇÃO [transacao={}]", transacaoForm, ex);
            throw new GlobalException("Existe outra transação em andamento. Tente novamente!");
        }
    }

    @Override
    public void validarCartao(Cartao cartao, TransacaoForm transacaoForm) {

        Optional<Cartao> optional = Optional.ofNullable(cartao);

        optional.orElseThrow(() -> new ValidarCartaoException(MensagemCartaoEnum.CARTAO_INEXISTENTE.name()));

        optional.map(Cartao::getSenha)
                .filter(senha -> StringUtils.equals(senha, transacaoForm.getSenha()))
                .orElseThrow(() -> new ValidarCartaoException(MensagemCartaoEnum.SENHA_INVALIDA.name()));

        optional.map(Cartao::getSaldo)
                .filter(saldo -> saldo.compareTo(transacaoForm.getValor()) >= 0)
                .orElseThrow(() -> new ValidarCartaoException(MensagemCartaoEnum.SALDO_INSUFICIENTE.name()));
    }
}
