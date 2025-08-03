package io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;

import java.util.regex.Pattern;

public class EmailValidationStrategy implements ChavePixValidationStrategy {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public boolean supports(TipoChaveEnum tipoChave) {
        return TipoChaveEnum.EMAIL.equals(tipoChave);
    }

    @Override
    public boolean isValid(String valor) {
        return valor != null && EMAIL_PATTERN.matcher(valor).matches();
    }
}