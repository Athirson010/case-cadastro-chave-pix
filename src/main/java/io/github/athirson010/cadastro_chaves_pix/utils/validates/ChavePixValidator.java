package io.github.athirson010.cadastro_chaves_pix.utils.validates;

import io.github.athirson010.cadastro_chaves_pix.domains.requests.CadastroChavePixRequest;

public interface ChavePixValidator {
   void validarChave(CadastroChavePixRequest request);
}
