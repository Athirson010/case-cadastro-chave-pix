package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoPessoaEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "contas")
public class ContaDocument {
    @Id
    private String id;
    private TipoContaEnum tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private TipoPessoaEnum tipoPessoa;
    private LocalDateTime dataInclusao;

    public ContaDocument() {
    }

    public ContaDocument(String id, TipoContaEnum tipoConta, String numeroAgencia, 
                        String numeroConta, String nomeCorrentista, 
                        TipoPessoaEnum tipoPessoa, LocalDateTime dataInclusao) {
        this.id = id;
        this.tipoConta = tipoConta;
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = numeroConta;
        this.nomeCorrentista = nomeCorrentista;
        this.tipoPessoa = tipoPessoa;
        this.dataInclusao = dataInclusao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoContaEnum getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoContaEnum tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeCorrentista() {
        return nomeCorrentista;
    }

    public void setNomeCorrentista(String nomeCorrentista) {
        this.nomeCorrentista = nomeCorrentista;
    }

    public TipoPessoaEnum getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoaEnum tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
}