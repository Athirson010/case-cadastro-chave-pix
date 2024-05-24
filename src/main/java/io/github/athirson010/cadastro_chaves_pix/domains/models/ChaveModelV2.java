package io.github.athirson010.cadastro_chaves_pix.domains.models;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Document("chaveV2")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChaveModelV2 extends AbstractModel {
    private String valorChave;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataInativacao;
    private StatusChaveEnum status;
    private TipoChaveEnum tipoChave;
    private String contaId;

    //TODO Revisar para fazer direto com a query
    public static List<ChaveModelV2> filtrarIntervalosDatas(List<ChaveModelV2> chaves, ChaveModelV2 filtro) {
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
            LocalDateTime inativacaoComeco = filtro.getDataInativacao();
            LocalDateTime inativacaoFinal = LocalDateTime.of(LocalDate.from(filtro.getDataInativacao()), LocalTime.MAX);

            return chaves.stream()
                    .filter(chave -> chave.getDataInativacao() != null &&
                            !chave.getDataInativacao().isBefore(inativacaoComeco) &&
                            !chave.getDataInativacao().isAfter(inativacaoFinal))
                    .collect(Collectors.toList());
        }
        return chaves;
    }

}
