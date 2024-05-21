package io.github.athirson010.cadastro_chaves_pix.dados;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum.FISICA;

public class ChaveMassa {
public static CadastroChavePixRequest cadastroChavePixRequest(){
    return new CadastroChavePixRequest(TipoChaveEnum.CPF, "4827447841",
            TipoContaEnum.CORRENTE, "1234", "12345678",
            "TESTE", "TESTE", FISICA);
}

}
