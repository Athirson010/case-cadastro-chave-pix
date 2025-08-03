package io.github.athirson010.cadastro_chaves_pix.application.ports.output;

import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;

import java.util.Optional;

public interface ContaRepositoryPort {
    Conta salvar(Conta conta);
    Optional<Conta> buscarPorId(ContaId id);
    Optional<Conta> buscarPorAgenciaEConta(NumeroAgencia agencia, NumeroConta conta);
    void deletar(ContaId id);
}