package io.github.athirson010.cadastro_chaves_pix.dados;

import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum.FISICA;

public class ChaveMassa {
    static String CPF = "4827447841";
    static String NOME_CORRENTISTA = "TESTE";
    static String SOBRENOME_CORRENTISTA = "V2";
    static String NUMERO_AGENCIA = "1234";
    static String NUMERO_CONTA = "1234";


    public static CadastroChavePixRequest cadastroChavePixRequest() {
        return new CadastroChavePixRequest(TipoChaveEnum.CPF, CPF,
                TipoContaEnum.CORRENTE, NUMERO_AGENCIA, NUMERO_CONTA,
                NOME_CORRENTISTA, SOBRENOME_CORRENTISTA, FISICA);
    }

    public static AtualizarChavePixRequest atualizarChavePixRequest() {
        return new AtualizarChavePixRequest(TipoContaEnum.CORRENTE, NUMERO_AGENCIA, NUMERO_CONTA,
                NOME_CORRENTISTA, SOBRENOME_CORRENTISTA, TipoChaveEnum.CPF, CPF);
    }
}
