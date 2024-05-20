package io.github.athirson010.cadastro_chaves_pix.domains.models;


import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Data
public class AbstractModel {
    private String id;

    @LastModifiedDate
    private LocalDate ultimaAtualizacao;
}
