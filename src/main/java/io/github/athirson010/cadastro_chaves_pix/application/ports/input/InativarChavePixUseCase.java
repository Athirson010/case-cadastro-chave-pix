package io.github.athirson010.cadastro_chaves_pix.application.ports.input;

import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;

public interface InativarChavePixUseCase {
    void executar(ChavePixId chavePixId);
}