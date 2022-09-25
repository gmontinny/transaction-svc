package br.com.coffeeandit.transactionsvc.domain.validator.impl;

import br.com.coffeeandit.transactionsvc.domain.exception.DomainBusinessException;
import br.com.coffeeandit.transactionsvc.domain.validator.TransactionValidator;
import br.com.coffeeandit.transactionsvc.dto.RequestTransactionDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnProperty(
        value = "transaction.validation.banco",
        havingValue = "true",
        matchIfMissing = false)
public class BancoTransactionValidator implements TransactionValidator {

    public static final int CODIGO_BANCO = 785;

    @Override
    public void validate(final RequestTransactionDto requestTransactionDto) throws DomainBusinessException {

        if (Objects.isNull(requestTransactionDto.getBeneficiario())) {
            throw new DomainBusinessException("Inválido banco do beneficiário");
        } else if (Objects.isNull(requestTransactionDto.getBeneficiario().getCodigoBanco()) ||
                requestTransactionDto.getBeneficiario().getCodigoBanco().compareTo((long) CODIGO_BANCO) != 0
        ) {
            throw new DomainBusinessException("Inválido banco do beneficiário");
        }

    }
}

