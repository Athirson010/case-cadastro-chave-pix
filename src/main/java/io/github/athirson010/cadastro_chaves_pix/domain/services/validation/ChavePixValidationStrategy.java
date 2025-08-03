package io.github.athirson010.cadastro_chaves_pix.domain.services.validation;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;

public interface ChavePixValidationStrategy {
    boolean supports(TipoChaveEnum tipoChave);
    boolean isValid(String valor);
}