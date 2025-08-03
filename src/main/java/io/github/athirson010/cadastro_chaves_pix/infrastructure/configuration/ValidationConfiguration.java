package io.github.athirson010.cadastro_chaves_pix.infrastructure.configuration;

import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationService;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.ChavePixValidationStrategy;
import io.github.athirson010.cadastro_chaves_pix.domain.services.validation.strategies.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfiguration {

    @Bean
    public List<ChavePixValidationStrategy> estrategiasValidacaoChavePix() {
        return List.of(
                new CpfValidationStrategy(),
                new CnpjValidationStrategy(),
                new EmailValidationStrategy(),
                new CelularValidationStrategy(),
                new AleatoriaValidationStrategy()
        );
    }

    @Bean
    public ChavePixValidationService servicoValidacaoChavePix(List<ChavePixValidationStrategy> estrategias) {
        return new ChavePixValidationService(estrategias);
    }
}