package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Document("chave")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChaveModel extends AbstractModel {
    private String nomeCorrentista;
    private String numeroAgencia;
    private String numeroConta;
    private TipoContaEnum tipoConta;
    private TipoPessoaEnum tipoPessoa;
    private TipoChaveEnum tipoChave;
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private StatusChaveEnum status;


    //TODO Revisar para fazer direto com a query
    public static List<ChaveModel> filtrarIntervalosDatas(List<ChaveModel> chaves, ChaveModel filtro) {

        if (filtro.getDataInclusao() != null) {
            LocalDateTime inclusaoComeco = filtro.getDataInclusao();
            LocalDateTime inclusaoFinal = LocalDateTime.of(LocalDate.from(filtro.getDataInclusao()), LocalTime.MAX);

            return chaves.stream()
                    .filter(chave -> chave.getDataInclusao() != null &&
                            !chave.getDataInclusao().isBefore(inclusaoComeco) &&
                            !chave.getDataInclusao().isAfter(inclusaoFinal))
                    .collect(Collectors.toList());
        }

        if (filtro.getDataInativacao() != null) {
            LocalDateTime inclusaoComeco = filtro.getDataInativacao();
            LocalDateTime inclusaoFinal = LocalDateTime.of(LocalDate.from(filtro.getDataInativacao()), LocalTime.MAX);

            return chaves.stream()
                    .filter(chave -> chave.getDataInclusao() != null &&
                            !chave.getDataInativacao().isBefore(inclusaoComeco) &&
                            !chave.getDataInativacao().isAfter(inclusaoFinal))
                    .collect(Collectors.toList());
        }
        return chaves;
    }
}


