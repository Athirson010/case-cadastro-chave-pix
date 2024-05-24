package io.github.athirson010.cadastro_chaves_pix.services.v2;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveV2Repository;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChaveServiceV2Test {
    private final ChaveV2Repository repository = mock(ChaveV2Repository.class);
    private final ChaveServiceV2 service = new ChaveServiceV2(repository);
    ChaveModelV2 chaveModel = ChaveV2Mapper.of(ChaveMassa.cadastroChavePixRequest(), new AbstractModel("1", LocalDate.now()));

    @Test
    void testBuscarChavePorValorChaveAtiva() {
        String valorChave = "12345678909";
        chaveModel.setStatus(StatusChaveEnum.A);

        when(repository.findByValorChaveAndStatus(valorChave, StatusChaveEnum.A))
                .thenReturn(Optional.of(chaveModel));

        Optional<ChaveModelV2> result = service.buscarChavePorValorChave(valorChave);
        assertEquals(chaveModel, result.orElse(null));
    }

    @Test
    void testBuscarChavePorValorChaveInativa() {
        String valorChave = "12345678909";

        when(repository.findByValorChaveAndStatus(valorChave, StatusChaveEnum.A))
                .thenReturn(Optional.empty());

        Optional<ChaveModelV2> result = service.buscarChavePorValorChave(valorChave);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testBuscarTudo() {
        ChaveModelV2 chave = new ChaveModelV2();
        Example<ChaveModelV2> example = Example.of(chave);
        List<ChaveModelV2> listaEsperada = List.of(new ChaveModelV2());

        when(repository.findAll(example)).thenReturn(listaEsperada);

        List<ChaveModelV2> result = service.buscarTudo(example);
        assertEquals(listaEsperada, result);
    }

    @Test
    void buscarQuantidadeChavesAtivasPorConta() {

        when(repository.countByContaIdAndStatus(any(), any()))
                .thenReturn(2);

        int result = service.buscarQuantidadeChavesAtivasPorConta("1");

        assertEquals(2, result);
    }
}