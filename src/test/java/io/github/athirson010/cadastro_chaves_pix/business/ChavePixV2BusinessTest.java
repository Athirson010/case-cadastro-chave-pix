package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ContaResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ChaveServiceV2;
import io.github.athirson010.cadastro_chaves_pix.services.v2.ContaServiceV2;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ChavePixV2BusinessTest {
    @Mock
    private ChaveServiceV2 chaveServiceMock;
    @Mock
    private ContaServiceV2 contaServiceMock;
    @InjectMocks
    private ChavePixV2Business chavePixBusiness;
    private AtualizarChavePixRequest atualizarChavePixRequest;
    private CadastroChavePixRequest cadastroChavePixRequest;
    ChaveModelV2 chaveModelV2;
    ChaveModel chaveModel;
    ContaModelV2 contaModelV2;
    static String ID = "10";

    public ChavePixV2BusinessTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setup() {
        cadastroChavePixRequest = ChaveMassa.cadastroChavePixRequest();
        atualizarChavePixRequest = ChaveMassa.atualizarChavePixRequest();
        chaveModelV2 = ChaveV2Mapper.of(atualizarChavePixRequest);
        chaveModel = ChaveMapper.of(cadastroChavePixRequest);
        contaModelV2 = ContaV2Mapper.of(cadastroChavePixRequest);
    }

    @Test
     void testCriarChaveComConta() {
        chaveModel.setId(ID);

        when(contaServiceMock.buscarContaPorNumeroContaENumeroAgencia(anyString(), anyString()))
                .thenReturn(Optional.of(contaModelV2));

        when(chaveServiceMock.save(any()))
                .thenReturn(chaveModel);

        CadastroChavePixResponse actualResponse = chavePixBusiness
                .criarChaveComConta(cadastroChavePixRequest);

        assertEquals(ID, actualResponse.getId());
    }

    @Test
     void testCriarChaveComConta_ErroLimiteChaves() {
        chaveModel.setId(ID);

        when(contaServiceMock.findById(anyString()))
                .thenReturn(contaModelV2);

        when(contaServiceMock.buscarContaPorNumeroContaENumeroAgencia(anyString(), anyString()))
                .thenReturn(Optional.of(contaModelV2));

        when(chaveServiceMock.buscarQuantidadeChavesAtivasPorConta(any()))
                .thenReturn(20);

        when(chaveServiceMock.save(any()))
                .thenReturn(chaveModel);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.criarChaveComConta(cadastroChavePixRequest));

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testInativarChavePixChaveErroInativa() {
        when(chaveServiceMock.findById(any()))
                .thenReturn(chaveModelV2);

        chaveModelV2.setStatus(StatusChaveEnum.INATIVA);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.inativarChavePix(ID));

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testInativarChavePixChaveSucesso() {
        chaveModelV2 = ChaveV2Mapper.of(cadastroChavePixRequest, new AbstractModel("1", LocalDate.now()));
        chaveModelV2.setId(ID);

        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModelV2);

        when(chaveServiceMock.update(any(), any()))
                .thenReturn(chaveModelV2);

        when(contaServiceMock.findById(chaveModelV2.getContaId()))
                .thenReturn(contaModelV2);

        ChavePixResponse actualResponse = chavePixBusiness.inativarChavePix(ID);

        assertEquals(ID, actualResponse.getId());
    }

    @Test
    void testBuscarChavesErroNaoEncontrado() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModelV2);
        chaveModelV2.setId(ID);

        Example<ChaveModelV2> exampleChave = Example.of(chaveModelV2,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        Example<ContaModelV2> exampleConta = Example.of(contaModelV2,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnoreCase());

        NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                () -> chavePixBusiness.buscarChaves(exampleChave, exampleConta));

        assertEquals("404 NOT_FOUND \"Criterio(s) não encontrado!\"", exception.getMessage());
    }

    @Test
    void testBuscarChavesSucesso() {
        chaveModelV2.setContaId(ID);
        contaModelV2.setId(ID);

        when(chaveServiceMock.buscarTudo(any()))
                .thenReturn(List.of(chaveModelV2));

        when(contaServiceMock.buscarTudo(any()))
                .thenReturn(List.of(contaModelV2));

        Example<ChaveModelV2> exampleChave = Example.of(chaveModelV2,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        Example<ContaModelV2> exampleConta = Example.of(contaModelV2,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnoreCase());

        List<ContaResponseV2> chavePixResponses = chavePixBusiness.buscarChaves(exampleChave, exampleConta);

        assertEquals(1, chavePixResponses.size());
    }

    @Test
    void testAtualizarChavePixErroChaveInativa() {
        chaveModelV2.setStatus(StatusChaveEnum.INATIVA);

        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModelV2);

        atualizarChavePixRequest = ChaveMassa.atualizarChavePixRequest();

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.atualizarChavePix(ID, atualizarChavePixRequest));

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testAtualizarChavePixSucesso() {
        chaveModelV2.setStatus(StatusChaveEnum.ATIVA);
        chaveModelV2.setId(ID);

        when(contaServiceMock.update(any(), any()))
                .thenReturn(contaModelV2);
        when(chaveServiceMock.update(any(), any()))
                .thenReturn(chaveModelV2);
        when(chaveServiceMock.findById(any()))
                .thenReturn(chaveModelV2);

        atualizarChavePixRequest = ChaveMassa.atualizarChavePixRequest();

        ContaResponseV2 chavePixResponses = chavePixBusiness.atualizarChavePix(ID, atualizarChavePixRequest);

        assertEquals(ID, chavePixResponses.getChaves().get(0).getId());
    }
}