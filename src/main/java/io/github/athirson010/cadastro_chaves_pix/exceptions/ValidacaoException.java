package io.github.athirson010.cadastro_chaves_pix.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ValidacaoException extends ResponseStatusException {

    private String detalhes;

    public ValidacaoException(String detalhes) {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
        this.detalhes = detalhes;
    }
}
