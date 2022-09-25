package br.com.coffeeandit.transactionsvc.domain;

import br.com.coffeeandit.transactionsvc.domain.validator.TransactionValidation;
import br.com.coffeeandit.transactionsvc.dto.Conta;
import br.com.coffeeandit.transactionsvc.dto.RequestTransactionDto;
import br.com.coffeeandit.transactionsvc.dto.TransactionDto;
import br.com.coffeeandit.transactionsvc.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class TransactionBusiness {

    public TransactionBusiness(@Qualifier("emptyTransactionJavaOlderTheanSevenTeenValidation") TransactionValidation transactionValidation, TransactionRepository transactionRepository) {
        this.transactionValidation = transactionValidation;
        this.transactionRepository = transactionRepository;
    }

    private final TransactionValidation transactionValidation;
    private final TransactionRepository transactionRepository;

    public void save(final RequestTransactionDto requestTransactionDto) {
        if (Objects.isNull(requestTransactionDto.getData()))
            requestTransactionDto.setData(LocalDateTime.now());
        transactionValidation.validate(requestTransactionDto);
        transactionRepository.save(requestTransactionDto);
    }

    public void atualizarTransacao(TransactionDto transactionDto) {
        log.info("Atualizando transação: {}", transactionDto);
        transactionRepository.save(transactionDto);
    }

    public List<TransactionDto> findByConta(final Long codigoAgencia, final Long codigoConta) {
        var conta = new Conta();
        conta.setCodigoConta(codigoConta);
        conta.setCodigoAgencia(codigoAgencia);
        return transactionRepository.findByConta(conta);
    }

    public void aprovarTransacao(TransactionDto transEvent) {
        var transacao = buscarTransacao(transEvent);
        if (transacao.isPresent()) {
            var transDB = transacao.get();
            if (!transDB.isAnalisada() && transEvent.isAnalisada()) {
                transDB.aprovar();
                atualizarTransacao(transDB);
                log.info("Transação aprovada: {}", transDB);
            }
        }
    }

    public Optional<TransactionDto> buscarTransacao(@NotNull TransactionDto transactionDto) {
        return transactionRepository.findById(transactionDto.getUuid());
    }

}


