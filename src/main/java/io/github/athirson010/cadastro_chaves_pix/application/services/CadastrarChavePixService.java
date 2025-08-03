package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.CadastrarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.ChaveDuplicadaException;
import io.github.athirson010.cadastro_chaves_pix.domain.exceptions.LimiteChavesExcedidoException;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.*;

import java.time.LocalDateTime;

public class CadastrarChavePixService implements CadastrarChavePixUseCase {

    private final ChavePixRepositoryPort repositorioChavePix;
    private final ContaRepositoryPort repositorioConta;

    public CadastrarChavePixService(ChavePixRepositoryPort repositorioChavePix, 
                                  ContaRepositoryPort repositorioConta) {
        this.repositorioChavePix = repositorioChavePix;
        this.repositorioConta = repositorioConta;
    }

    @Override
    public ChavePix executar(CadastrarChavePixCommand comando) {
        ChavePixValue valorChave = ChavePixValue.of(comando.valorChave());
        
        validarChaveUnica(valorChave);
        
        Conta conta = obterOuCriarConta(comando);
        
        validarLimiteChaves(conta);
        
        ChavePix chavePix = new ChavePix(
                ChavePixId.generate(),
                conta.getId(),
                comando.tipoChave(),
                valorChave,
                LocalDateTime.now()
        );
        
        return repositorioChavePix.salvar(chavePix);
    }

    private void validarChaveUnica(ChavePixValue valorChave) {
        if (repositorioChavePix.existePorValor(valorChave)) {
            throw new ChaveDuplicadaException("Chave PIX já cadastrada");
        }
    }

    private Conta obterOuCriarConta(CadastrarChavePixCommand comando) {
        NumeroAgencia agencia = NumeroAgencia.of(comando.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(comando.numeroConta());
        
        return repositorioConta.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseGet(() -> criarNovaConta(comando, agencia, numeroConta));
    }

    private Conta criarNovaConta(CadastrarChavePixCommand comando, 
                                NumeroAgencia agencia, NumeroConta numeroConta) {
        Conta novaConta = new Conta(
                ContaId.generate(),
                comando.tipoConta(),
                agencia,
                numeroConta,
                comando.nomeCorrentista(),
                comando.tipoPessoa()
        );
        return repositorioConta.salvar(novaConta);
    }

    private void validarLimiteChaves(Conta conta) {
        long chavesAtivas = repositorioChavePix.contarChavesAtivasPorConta(conta.getId());
        if (chavesAtivas >= conta.getLimiteChavesPix()) {
            throw new LimiteChavesExcedidoException(
                    String.format("Limite de %d chaves PIX excedido para esta conta", 
                            conta.getLimiteChavesPix())
            );
        }
    }
}