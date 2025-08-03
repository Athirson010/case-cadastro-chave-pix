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

    private final ChavePixRepositoryPort chavePixRepository;
    private final ContaRepositoryPort contaRepository;

    public AtualizarChavePixService(ChavePixRepositoryPort chavePixRepository, 
                                  ContaRepositoryPort contaRepository) {
        this.chavePixRepository = chavePixRepository;
        this.contaRepository = contaRepository;
    }

    @Override
    public ChavePix executar(AtualizarChavePixCommand command) {
        ChavePixId chavePixId = ChavePixId.of(command.chavePixId());
        
        ChavePix chavePix = chavePixRepository.buscarPorId(chavePixId)
                .orElseThrow(() -> new ChavePixNaoEncontradaException("Chave PIX não encontrada"));

        if (!chavePix.isAtiva()) {
            throw new IllegalStateException("Não é possível atualizar uma chave PIX inativa");
        }
        
        Conta contaAtualizada = obterOuCriarConta(command);
        
        return chavePixRepository.salvar(chavePix);
    }

    private Conta obterOuCriarConta(AtualizarChavePixCommand command) {
        NumeroAgencia agencia = NumeroAgencia.of(command.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(command.numeroConta());
        
        return contaRepository.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }
}