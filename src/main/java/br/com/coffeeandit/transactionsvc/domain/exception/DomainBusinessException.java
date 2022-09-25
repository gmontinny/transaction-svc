package br.com.coffeeandit.transactionsvc.domain.exception;

public class DomainBusinessException extends RuntimeException {
    public DomainBusinessException(String message) {
        super(message);
    }
}

