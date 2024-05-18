package io.github.athirson010.cadastro_chaves_pix.domains.entity;

import io.github.athirson010.cadastro_chaves_pix.domains.AbstractModel;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("chave")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChaveEntity extends AbstractModel {
    @DBRef
    @NotNull
    private ContaEntity conta;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private StatusChaveEnum status;
}
