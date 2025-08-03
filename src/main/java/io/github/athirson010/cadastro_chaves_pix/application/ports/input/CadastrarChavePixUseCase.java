package io.github.athirson010.cadastro_chaves_pix.application.ports.input;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarChavePixCommand;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;

public interface CadastrarChavePixUseCase {
    ChavePix executar(CadastrarChavePixCommand command);
}