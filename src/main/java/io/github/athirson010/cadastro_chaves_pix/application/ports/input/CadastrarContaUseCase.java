package io.github.athirson010.cadastro_chaves_pix.application.ports.input;

import io.github.athirson010.cadastro_chaves_pix.application.dto.CadastrarContaCommand;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;

public interface CadastrarContaUseCase {
    Conta executar(CadastrarContaCommand comando);
}