package io.github.athirson010.cadastro_chaves_pix.domain.exceptions;

public class ChavePixNaoEncontradaException extends RuntimeException {
    public ChavePixNaoEncontradaException(String message) {
        super(message);
    }
}