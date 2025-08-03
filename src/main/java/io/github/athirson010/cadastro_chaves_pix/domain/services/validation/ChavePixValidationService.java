package io.github.athirson010.cadastro_chaves_pix.domain.services.validation;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;

import java.util.List;

public class ChavePixValidationService {
    
    private final List<ChavePixValidationStrategy> estrategias;

    public ChavePixValidationService(List<ChavePixValidationStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public boolean validar(TipoChaveEnum tipoChave, String valor) {
        return estrategias.stream()
                .filter(estrategia -> estrategia.supports(tipoChave))
                .findFirst()
                .map(estrategia -> estrategia.isValid(valor))
                .orElse(false);
    }
}