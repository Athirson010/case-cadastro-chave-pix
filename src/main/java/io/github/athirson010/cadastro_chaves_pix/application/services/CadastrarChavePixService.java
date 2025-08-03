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

    private final ChavePixRepositoryPort chavePixRepository;
    private final ContaRepositoryPort contaRepository;

    public CadastrarChavePixService(ChavePixRepositoryPort chavePixRepository, 
                                  ContaRepositoryPort contaRepository) {
        this.chavePixRepository = chavePixRepository;
        this.contaRepository = contaRepository;
    }

    @Override
    public ChavePix executar(CadastrarChavePixCommand command) {
        ChavePixValue valorChave = ChavePixValue.of(command.valorChave());
        
        validarChaveUnica(valorChave);
        
        Conta conta = obterOuCriarConta(command);
        
        validarLimiteChaves(conta);
        
        ChavePix chavePix = new ChavePix(
                ChavePixId.generate(),
                conta.getId(),
                command.tipoChave(),
                valorChave,
                LocalDateTime.now()
        );
        
        return chavePixRepository.salvar(chavePix);
    }

    private void validarChaveUnica(ChavePixValue valorChave) {
        if (chavePixRepository.existePorValor(valorChave)) {
            throw new ChaveDuplicadaException("Chave PIX já cadastrada");
        }
    }

    private Conta obterOuCriarConta(CadastrarChavePixCommand command) {
        NumeroAgencia agencia = NumeroAgencia.of(command.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(command.numeroConta());
        
        return contaRepository.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseGet(() -> criarNovaConta(command, agencia, numeroConta));
    }

    private Conta criarNovaConta(CadastrarChavePixCommand command, 
                                NumeroAgencia agencia, NumeroConta numeroConta) {
        Conta novaConta = new Conta(
                ContaId.generate(),
                command.tipoConta(),
                agencia,
                numeroConta,
                command.nomeCorrentista(),
                command.tipoPessoa()
        );
        return contaRepository.salvar(novaConta);
    }

    private void validarLimiteChaves(Conta conta) {
        long chavesAtivas = chavePixRepository.contarChavesAtivasPorConta(conta.getId());
        if (chavesAtivas >= conta.getLimiteChavesPix()) {
            throw new LimiteChavesExcedidoException(
                    String.format("Limite de %d chaves PIX excedido para esta conta", 
                            conta.getLimiteChavesPix())
            );
        }
    }
}