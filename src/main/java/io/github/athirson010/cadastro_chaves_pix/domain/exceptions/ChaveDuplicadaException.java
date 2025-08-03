package io.github.athirson010.cadastro_chaves_pix.domain.exceptions;

public class ChaveDuplicadaException extends RuntimeException {
    public ChaveDuplicadaException(String message) {
        super(message);
    }
}