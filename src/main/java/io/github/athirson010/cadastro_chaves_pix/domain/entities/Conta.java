package io.github.athirson010.cadastro_chaves_pix.domain.entities;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;

import java.time.LocalDateTime;
import java.util.Objects;

public class Conta {
    private final ContaId id;
    private final TipoContaEnum tipoConta;
    private final NumeroAgencia numeroAgencia;
    private final NumeroConta numeroConta;
    private final String nomeCorrentista;
    private final TipoPessoaEnum tipoPessoa;
    private final LocalDateTime dataInclusao;

    public Conta(ContaId id, TipoContaEnum tipoConta, NumeroAgencia numeroAgencia, 
                 NumeroConta numeroConta, String nomeCorrentista, TipoPessoaEnum tipoPessoa) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.tipoConta = Objects.requireNonNull(tipoConta, "Tipo de conta não pode ser nulo");
        this.numeroAgencia = Objects.requireNonNull(numeroAgencia, "Número da agência não pode ser nulo");
        this.numeroConta = Objects.requireNonNull(numeroConta, "Número da conta não pode ser nulo");
        this.nomeCorrentista = Objects.requireNonNull(nomeCorrentista, "Nome do correntista não pode ser nulo");
        this.tipoPessoa = Objects.requireNonNull(tipoPessoa, "Tipo de pessoa não pode ser nulo");
        this.dataInclusao = LocalDateTime.now();
        validateBusinessRules();
    }

    private void validateBusinessRules() {
        if (nomeCorrentista.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do correntista não pode ser vazio");
        }
        if (nomeCorrentista.length() > 30) {
            throw new IllegalArgumentException("Nome do correntista não pode ter mais de 30 caracteres");
        }
    }

    public int getLimiteChavesPix() {
        return switch (tipoPessoa) {
            case PESSOA_FISICA -> 5;
            case PESSOA_JURIDICA -> 20;
        };
    }

    public ContaId getId() {
        return id;
    }

    public TipoContaEnum getTipoConta() {
        return tipoConta;
    }

    public NumeroAgencia getNumeroAgencia() {
        return numeroAgencia;
    }

    public NumeroConta getNumeroConta() {
        return numeroConta;
    }

    public String getNomeCorrentista() {
        return nomeCorrentista;
    }

    public TipoPessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(id, conta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", tipoConta=" + tipoConta +
                ", numeroAgencia=" + numeroAgencia +
                ", numeroConta=" + numeroConta +
                ", nomeCorrentista='" + nomeCorrentista + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                '}';
    }
}