package io.github.athirson010.cadastro_chaves_pix.infrastructure.configuration;

import io.github.athirson010.cadastro_chaves_pix.application.ports.input.*;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.application.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CadastrarChavePixUseCase cadastrarChavePixUseCase(ChavePixRepositoryPort chavePixRepository,
                                                           ContaRepositoryPort contaRepository) {
        return new CadastrarChavePixService(chavePixRepository, contaRepository);
    }

    @Bean
    public AtualizarChavePixUseCase atualizarChavePixUseCase(ChavePixRepositoryPort chavePixRepository,
                                                           ContaRepositoryPort contaRepository) {
        return new AtualizarChavePixService(chavePixRepository, contaRepository);
    }

    @Bean
    public InativarChavePixUseCase inativarChavePixUseCase(ChavePixRepositoryPort chavePixRepository) {
        return new InativarChavePixService(chavePixRepository);
    }

    @Bean
    public BuscarChavesPixUseCase buscarChavesPixUseCase(ChavePixRepositoryPort chavePixRepository) {
        return new BuscarChavesPixService(chavePixRepository);
    }

    @Bean
    public CadastrarContaUseCase cadastrarContaUseCase(ContaRepositoryPort contaRepository) {
        return new CadastrarContaService(contaRepository);
    }
}