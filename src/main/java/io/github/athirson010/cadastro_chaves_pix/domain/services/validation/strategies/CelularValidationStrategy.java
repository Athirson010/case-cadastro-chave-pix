package io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;

import java.util.regex.Pattern;

public class CelularValidationStrategy implements ChavePixValidationStrategy {
    
    private static final Pattern CELULAR_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

    @Override
    public boolean supports(TipoChaveEnum tipoChave) {
        return TipoChaveEnum.CELULAR.equals(tipoChave);
    }

    @Override
    public boolean isValid(String valor) {
        return valor != null && CELULAR_PATTERN.matcher(valor).matches();
    }
}