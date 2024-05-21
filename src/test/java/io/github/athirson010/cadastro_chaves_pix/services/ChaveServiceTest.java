package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ChaveServiceTest {
    private final ChaveRepository repository = mock(ChaveRepository.class);
    private final ChaveService service = new ChaveService(repository);

    @Test
    void testBuscarChavePorValorChaveAtiva() {
        String valorChave = "12345678909";
        ChaveModel chaveModel = new ChaveModel();
        chaveModel.setStatus(StatusChaveEnum.ATIVA);

        when(repository.findByValorChaveAndStatus(valorChave, StatusChaveEnum.ATIVA))
                .thenReturn(Optional.of(chaveModel));

        Optional<ChaveModel> result = service.buscarChavePorValorChave(valorChave);
        assertEquals(chaveModel, result.orElse(null));
    }

    @Test
    void testBuscarChavePorValorChaveInativa() {

        String valorChave = "12345678909";

        when(repository.findByValorChaveAndStatus(valorChave, StatusChaveEnum.ATIVA)).thenReturn(Optional.empty());

        Optional<ChaveModel> result = service.buscarChavePorValorChave(valorChave);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testBuscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia() {
        String numeroConta = "12345";
        String numeroAgencia = "67890";
        TipoPessoaEnum tipoPessoa = TipoPessoaEnum.FISICA;
        int expectedCount = 5;

        when(repository.countByNumeroContaAndNumeroAgenciaAndStatus(numeroConta, numeroAgencia, StatusChaveEnum.ATIVA))
                .thenReturn(expectedCount);

        int result = service.buscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia(numeroConta, numeroAgencia, tipoPessoa);
        assertEquals(expectedCount, result);
    }

    @Test
    void testBuscarTudo() {
        ChaveModel exampleChaveModel = new ChaveModel();
        Example<ChaveModel> example = Example.of(exampleChaveModel);
        List<ChaveModel> expectedList = List.of(new ChaveModel(), new ChaveModel());

        when(repository.findAll(example)).thenReturn(expectedList);

        List<ChaveModel> result = service.buscarTudo(example);
        assertEquals(expectedList, result);
    }
}
