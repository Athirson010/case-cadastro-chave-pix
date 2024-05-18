package io.github.athirson010.cadastro_chaves_pix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NaoAutorizadoException extends ResponseStatusException {
    public NaoAutorizadoException(String motivo) {
        super(HttpStatus.UNAUTHORIZED, motivo);
    }
}
