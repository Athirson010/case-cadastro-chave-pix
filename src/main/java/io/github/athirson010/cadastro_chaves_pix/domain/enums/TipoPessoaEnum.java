package io.github.athirson010.cadastro_chaves_pix.domain.enums;

public enum TipoPessoaEnum {
    PESSOA_FISICA(5),
    PESSOA_JURIDICA(20);

    private final int limiteChavesPix;

    TipoPessoaEnum(int limiteChavesPix) {
        this.limiteChavesPix = limiteChavesPix;
    }

    public int getLimiteChavesPix() {
        return limiteChavesPix;
    }
}