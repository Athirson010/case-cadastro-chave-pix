package io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;

import java.util.regex.Pattern;

public class AleatoriaValidationStrategy implements ChavePixValidationStrategy {
    
    private static final Pattern ALEATORIA_PATTERN = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

    @Override
    public boolean supports(TipoChaveEnum tipoChave) {
        return TipoChaveEnum.ALEATORIA.equals(tipoChave);
    }

    @Override
    public boolean isValid(String valor) {
        return valor != null && ALEATORIA_PATTERN.matcher(valor).matches();
    }
}