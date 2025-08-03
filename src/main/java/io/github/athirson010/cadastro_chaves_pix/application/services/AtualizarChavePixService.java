package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.AtualizarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.AtualizarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChavePixNaoEncontradaException;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;

public class AtualizarChavePixService implements AtualizarChavePixUseCase {

    private final ChavePixRepositoryPort repositorioChavePix;
    private final ContaRepositoryPort repositorioConta;

    public AtualizarChavePixService(ChavePixRepositoryPort repositorioChavePix, 
                                  ContaRepositoryPort repositorioConta) {
        this.repositorioChavePix = repositorioChavePix;
        this.repositorioConta = repositorioConta;
    }

    @Override
    public ChavePix executar(AtualizarChavePixCommand comando) {
        ChavePixId chavePixId = ChavePixId.of(comando.chavePixId());
        
        ChavePix chavePix = repositorioChavePix.buscarPorId(chavePixId)
                .orElseThrow(() -> new ChavePixNaoEncontradaException("Chave PIX não encontrada"));

        if (!chavePix.isAtiva()) {
            throw new IllegalStateException("Não é possível atualizar uma chave PIX inativa");
        }
        
        Conta contaAtualizada = obterOuCriarConta(comando);
        
        return repositorioChavePix.salvar(chavePix);
    }

    private Conta obterOuCriarConta(AtualizarChavePixCommand comando) {
        NumeroAgencia agencia = NumeroAgencia.of(comando.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(comando.numeroConta());
        
        return repositorioConta.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }
}