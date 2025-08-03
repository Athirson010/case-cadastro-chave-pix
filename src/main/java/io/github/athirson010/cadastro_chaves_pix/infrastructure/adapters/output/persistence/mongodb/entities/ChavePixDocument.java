package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domain.enums.TipoChaveEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chaves_pix")
public class ChavePixDocument {
    @Id
    private String id;
    private String contaId;
    private String tipoChave;
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private String status;

    public ChavePixDocument() {
    }

    public ChavePixDocument(String id, String contaId, String tipoChave,
                           String valorChave, LocalDateTime dataInclusao, 
                           LocalDateTime dataInativacao, String status) {
        this.id = id;
        this.contaId = contaId;
        this.tipoChave = tipoChave;
        this.valorChave = valorChave;
        this.dataInclusao = dataInclusao;
        this.dataInativacao = dataInativacao;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContaId() {
        return contaId;
    }

    public void setContaId(String contaId) {
        this.contaId = contaId;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(TipoChaveEnum tipoChave) {
        this.tipoChave = tipoChave.name();
    }

    public String getValorChave() {
        return valorChave;
    }

    public void setValorChave(String valorChave) {
        this.valorChave = valorChave;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(LocalDateTime dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(StatusChaveEnum status) {
        this.status = status.name();
    }
}