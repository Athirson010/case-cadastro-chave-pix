package io.github.athirson010.cadastro_chaves_pix.domain.services.factory;

import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationService;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies.*;

import java.util.List;

public class ChavePixValidationFactory {
    
    private ChavePixValidationFactory() {
    }

    public static ChavePixValidationService createValidationService() {
        List<ChavePixValidationStrategy> strategies = List.of(
                new CpfValidationStrategy(),
                new CnpjValidationStrategy(),
                new EmailValidationStrategy(),
                new CelularValidationStrategy(),
                new AleatoriaValidationStrategy()
        );
        
        return new ChavePixValidationService(strategies);
    }
}