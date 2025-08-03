package io.github.athirson010.cadastro_chaves_pix.domain.exceptions;

public class LimiteChavesExcedidoException extends RuntimeException {
    public LimiteChavesExcedidoException(String message) {
        super(message);
    }
}