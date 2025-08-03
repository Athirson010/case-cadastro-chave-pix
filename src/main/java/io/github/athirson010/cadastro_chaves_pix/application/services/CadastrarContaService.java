package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarContaCommand;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.CadastrarContaUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;

public class CadastrarContaService implements CadastrarContaUseCase {

    private final ContaRepositoryPort repositorioConta;

    public CadastrarContaService(ContaRepositoryPort repositorioConta) {
        this.repositorioConta = repositorioConta;
    }

    @Override
    public Conta executar(CadastrarContaCommand comando) {
        NumeroAgencia agencia = NumeroAgencia.of(comando.numeroAgencia());
        NumeroConta numeroConta = NumeroConta.of(comando.numeroConta());
        
        return repositorioConta.buscarPorAgenciaEConta(agencia, numeroConta)
                .orElseGet(() -> criarNovaConta(comando, agencia, numeroConta));
    }

    private Conta criarNovaConta(CadastrarContaCommand comando, 
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
}