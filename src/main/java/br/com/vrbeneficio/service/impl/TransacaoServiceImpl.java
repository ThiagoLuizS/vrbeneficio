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
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements ITransacaoService {

    private final ICartaoService cartaoService;

    @Override
    public Cartao transacao(TransacaoForm transacaoForm) {
        try {
            log.info(">> TRANSAÇÃO [transacaoForm={}]", transacaoForm);

            Queue<Runnable> taskQueue = new LinkedList<>();
            /*Executor de uma única thread para garantir que as tarefas sejam executadas uma por vez*/
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            AtomicReference<Cartao> cartao = new AtomicReference<>(Cartao.builder().build());
            taskQueue.add(() -> {
                log.info(">> TRANSAÇÃO [buscando cartão]");
                cartao.set(cartaoService.findByNumeroCartao(transacaoForm.getNumeroCartao()));
            });

            taskQueue.add(() -> {
               log.info(">> TRANSAÇÃO [validando cartão]");
                validarCartao(cartao.get(), transacaoForm);
            });

            taskQueue.add(() -> {
                log.info(">> TRANSAÇÃO [abatendo saldo cartão]");
                BigDecimal valor = transacaoForm.getValor().abs();
                cartao.get().setSaldo(cartao.get().getSaldo().subtract(valor));
            });

            CompletableFuture<Void> future = CompletableFuture.completedFuture(null);

            while (!taskQueue.isEmpty()) {
                Runnable task = taskQueue.poll();

                log.info("<< TRANSACAO [task={}]", task);

                if (task != null) {
                    future = future.thenRunAsync(task, executorService);
                }
            }

            /* Aguarda a conclusão de todas as tarefas */
            future.join();

            /* Encerra o ExecutorService */
            executorService.shutdown();

            return cartaoService.salvarToEntity(cartao.get());
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
