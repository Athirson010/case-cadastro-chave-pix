package io.github.athirson010.cadastro_chaves_pix.exceptions;

import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NaoEncontradoException extends ResponseStatusException {
    public NaoEncontradoException(String reason) {
        super(NOT_FOUND, reason + " não encontrado!");
    }

    public NaoEncontradoException() {
        super(NOT_FOUND);
    }
}
