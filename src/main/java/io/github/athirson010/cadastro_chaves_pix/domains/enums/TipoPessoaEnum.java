package io.github.athirson010.cadastro_chaves_pix.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPessoaEnum {
    FISICA(5),
    JURIDICA(20);

    private final Integer limitePermitido;
}
