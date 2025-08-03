package io.github.athirson010.cadastro_chaves_pix.domain.services.validation;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;

import java.util.List;

public class ChavePixValidationService {
    
    private final List<ChavePixValidationStrategy> strategies;

    public ChavePixValidationService(List<ChavePixValidationStrategy> strategies) {
        this.strategies = strategies;
    }

    public boolean validate(TipoChaveEnum tipoChave, String valor) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(tipoChave))
                .findFirst()
                .map(strategy -> strategy.isValid(valor))
                .orElse(false);
    }
}