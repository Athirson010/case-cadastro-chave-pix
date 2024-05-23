package io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FiltroChavePixRequest {
    private String id;
    private TipoChaveEnum tipoChave;
    private String agencia;
    private String conta;
    private String nomeCorrentista;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(example = "10/05/2024")
    private LocalDate dataInclusao;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @Schema(example = "10/05/2024")
    private LocalDate dataInativacao;

    public static void validarFiltro(FiltroChavePixRequest request){
        if (request.getDataInclusao() != null & request.getDataInativacao() != null) {
            throw new ValidacaoException("Não é permitido a combinacao de filtros. Data Inclusao & Data Inativacao");
        }

        if (request.getId() != null &&
                (request.getTipoChave() != null ||
                        request.getAgencia() != null ||
                        request.getDataInclusao() != null ||
                        request.getDataInativacao() != null ||
                        request.getNomeCorrentista() != null ||
                        request.getConta() != null)) {
            throw new ValidacaoException("Não é permitido a combinacao de filtros. ID com outros filtros");
        }
    }
}
