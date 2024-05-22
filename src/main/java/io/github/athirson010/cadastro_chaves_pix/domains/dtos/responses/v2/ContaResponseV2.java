package io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class ContaResponseV2 {
    private TipoContaEnum tipoConta;
    private String numeroAgencia;
    private String numeroConta;
    private String nomeCorrentista;
    private List<ChaveResponseV2> chaves = new ArrayList<>();
}
