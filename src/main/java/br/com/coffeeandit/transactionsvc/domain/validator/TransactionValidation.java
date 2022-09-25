package br.com.coffeeandit.transactionsvc.domain.validator;

import br.com.coffeeandit.transactionsvc.domain.exception.DomainBusinessException;
import br.com.coffeeandit.transactionsvc.dto.RequestTransactionDto;

@FunctionalInterface
public interface TransactionValidation {

    public void validate(final RequestTransactionDto requestTransactionDto) throws DomainBusinessException;

}

