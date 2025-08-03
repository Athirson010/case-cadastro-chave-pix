package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.ports.input.InativarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChavePixNaoEncontradaException;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;

public class InativarChavePixService implements InativarChavePixUseCase {

    private final ChavePixRepositoryPort chavePixRepository;

    public InativarChavePixService(ChavePixRepositoryPort chavePixRepository) {
        this.chavePixRepository = chavePixRepository;
    }

    @Override
    public void executar(ChavePixId chavePixId) {
        ChavePix chavePix = chavePixRepository.buscarPorId(chavePixId)
                .orElseThrow(() -> new ChavePixNaoEncontradaException("Chave PIX não encontrada"));

        chavePix.inativar();
        
        chavePixRepository.salvar(chavePix);
    }
}