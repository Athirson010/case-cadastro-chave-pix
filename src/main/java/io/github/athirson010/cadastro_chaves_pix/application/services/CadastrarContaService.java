package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarContaCommand;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.CadastrarContaUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;

public class CadastrarContaService implements CadastrarContaUseCase {

    private final ContaRepositoryPort contaRepository;

    public CadastrarContaService(ContaRepositoryPort contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Conta executar(CadastrarContaCommand command) {
        NumeroAgencia agencia = NumeroAgencia.of(command.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(command.numeroConta());
        
        return contaRepository.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseGet(() -> criarNovaConta(command, agencia, numeroConta));
    }

    private Conta criarNovaConta(CadastrarContaCommand command, 
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
}