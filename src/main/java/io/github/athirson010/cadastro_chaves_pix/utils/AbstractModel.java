package io.github.athirson010.cadastro_chaves_pix.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractModel {
    @Id
    private String id;

    @LastModifiedDate
    private LocalDate ultimaAtualizacao;
}
