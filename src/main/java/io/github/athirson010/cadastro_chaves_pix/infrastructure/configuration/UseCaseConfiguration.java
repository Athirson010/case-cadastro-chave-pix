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
    public CadastrarChavePixUseCase cadastrarChavePixUseCase(ChavePixRepositoryPort repositorioChavePix,
                                                           ContaRepositoryPort repositorioConta) {
        return new CadastrarChavePixService(repositorioChavePix, repositorioConta);
    }

    @Bean
    public AtualizarChavePixUseCase atualizarChavePixUseCase(ChavePixRepositoryPort repositorioChavePix,
                                                           ContaRepositoryPort repositorioConta) {
        return new AtualizarChavePixService(repositorioChavePix, repositorioConta);
    }

    @Bean
    public InativarChavePixUseCase inativarChavePixUseCase(ChavePixRepositoryPort repositorioChavePix) {
        return new InativarChavePixService(repositorioChavePix);
    }

    @Bean
    public BuscarChavesPixUseCase buscarChavesPixUseCase(ChavePixRepositoryPort repositorioChavePix) {
        return new BuscarChavesPixService(repositorioChavePix);
    }

    @Bean
    public CadastrarContaUseCase cadastrarContaUseCase(ContaRepositoryPort repositorioConta) {
        return new CadastrarContaService(repositorioConta);
    }
}