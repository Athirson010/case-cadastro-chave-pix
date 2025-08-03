package io.github.athirson010.cadastro_chaves_pix.domain.entities;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixValue;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChavePix {
    private final ChavePixId id;
    private final ContaId contaId;
    private final TipoChaveEnum tipoChave;
    private final ChavePixValue valorChave;
    private final LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private StatusChaveEnum status;

    public ChavePix(ChavePixId id, ContaId contaId, TipoChaveEnum tipoChave, 
                    ChavePixValue valorChave, LocalDateTime dataInclusao) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.contaId = Objects.requireNonNull(contaId, "Conta ID não pode ser nulo");
        this.tipoChave = Objects.requireNonNull(tipoChave, "Tipo de chave não pode ser nulo");
        this.valorChave = Objects.requireNonNull(valorChave, "Valor da chave não pode ser nulo");
        this.dataInclusao = Objects.requireNonNull(dataInclusao, "Data de inclusão não pode ser nula");
        this.status = StatusChaveEnum.ATIVA;
        validateBusinessRules();
    }

    public ChavePix(ChavePixId id, ContaId contaId, TipoChaveEnum tipoChave, 
                    ChavePixValue valorChave, LocalDateTime dataInclusao, 
                    LocalDateTime dataInativacao, StatusChaveEnum status) {
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.contaId = Objects.requireNonNull(contaId, "Conta ID não pode ser nulo");
        this.tipoChave = Objects.requireNonNull(tipoChave, "Tipo de chave não pode ser nulo");
        this.valorChave = Objects.requireNonNull(valorChave, "Valor da chave não pode ser nulo");
        this.dataInclusao = Objects.requireNonNull(dataInclusao, "Data de inclusão não pode ser nula");
        this.dataInativacao = dataInativacao;
        this.status = Objects.requireNonNull(status, "Status não pode ser nulo");
        validateBusinessRules();
    }

    private void validateBusinessRules() {
        if (!valorChave.isValidForType(tipoChave)) {
            throw new IllegalArgumentException("Valor da chave inválido para o tipo especificado");
        }
    }

    public void inativar() {
        if (this.status == StatusChaveEnum.INATIVA) {
            throw new IllegalStateException("Chave já está inativa");
        }
        this.status = StatusChaveEnum.INATIVA;
        this.dataInativacao = LocalDateTime.now();
    }

    public void ativar() {
        if (this.status == StatusChaveEnum.ATIVA) {
            throw new IllegalStateException("Chave já está ativa");
        }
        this.status = StatusChaveEnum.ATIVA;
        this.dataInativacao = null;
    }

    public boolean isAtiva() {
        return this.status == StatusChaveEnum.ATIVA;
    }

    public ChavePixId getId() {
        return id;
    }

    public ContaId getContaId() {
        return contaId;
    }

    public TipoChaveEnum getTipoChave() {
        return tipoChave;
    }

    public ChavePixValue getValorChave() {
        return valorChave;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public StatusChaveEnum getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChavePix chavePix = (ChavePix) o;
        return Objects.equals(id, chavePix.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChavePix{" +
                "id=" + id +
                ", contaId=" + contaId +
                ", tipoChave=" + tipoChave +
                ", valorChave=" + valorChave +
                ", status=" + status +
                '}';
    }
}