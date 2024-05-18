package io.github.athirson010.cadastro_chaves_pix.domains;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AbstractModel {
    @Id
    private UUID id;

    @LastModifiedDate
    private LocalDate ultimaAtualizacao;
}
