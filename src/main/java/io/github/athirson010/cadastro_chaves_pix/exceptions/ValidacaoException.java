package io.github.athirson010.cadastro_chaves_pix.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidacaoException extends ResponseStatusException {
    public ValidacaoException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
