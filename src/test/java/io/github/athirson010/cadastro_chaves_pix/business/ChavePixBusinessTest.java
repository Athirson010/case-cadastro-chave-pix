package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.NaoEncontradoException;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChavePixBusinessTest {
    @Mock
    private ChaveService chaveServiceMock;
    @InjectMocks
    private ChavePixBusiness chavePixBusiness;
    CadastroChavePixRequest request;
    ChaveModel chaveModel;
    static String ID = "10";
    public ChavePixBusinessTest() {
        MockitoAnnotations.initMocks(this);
    }
    @BeforeEach
    public void setup() {
        request = ChaveMassa.cadastroChavePixRequest();
        chaveModel = ChaveMapper.of(request);
    }
    @Test
    void testBuscarContaPorId() {
        chaveModel.setId(ID);
        when(chaveServiceMock.findById(any()))
                .thenReturn(chaveModel);

        ChavePixResponse actualResponse = chavePixBusiness.buscarPorId(ID);
        assertEquals(ID, actualResponse.getId());
    }
    @Test
    public void testCriarChaveComConta() {
        chaveModel.setId(ID);
        when(chaveServiceMock.save(any(ChaveModel.class)))
                .thenReturn(chaveModel);

        CadastroChavePixResponse actualResponse = chavePixBusiness.criarChaveComConta(request);

        assertEquals(ID, actualResponse.getId());
    }

    @Test
    public void testCriarChaveComConta_ErroLimiteChaves() {
        chaveModel.setId(ID);

        when(chaveServiceMock.buscarQuantidadeChavesAtivasPorNumeroContaENumeroAgencia(any(), any()))
                .thenReturn(100);

        when(chaveServiceMock.save(any(ChaveModel.class)))
                .thenReturn(chaveModel);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.criarChaveComConta(request));
        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testInativarChavePixChaveErroInativa() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);

        chaveModel.setStatus(StatusChaveEnum.INATIVA);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.inativarChavePix(ID));
        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testInativarChavePixChaveSucesso() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);
        chaveModel.setId(ID);

        when(chaveServiceMock.update(any(), any()))
                .thenReturn(chaveModel);

        ChavePixResponse actualResponse = chavePixBusiness.inativarChavePix(ID);

        assertEquals(ID, actualResponse.getId());
    }
    @Test
    void testBuscarChavesErroNaoEncontrado() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);
        chaveModel.setId(ID);

        Example<ChaveModel> example = Example.of(chaveModel,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        NaoEncontradoException exception = assertThrows(NaoEncontradoException.class,
                () -> chavePixBusiness.buscarChaves(example));

        assertEquals("404 NOT_FOUND \"Chaves não encontrado!\"", exception.getMessage());
    }
    @Test
    void testBuscarChavesSucesso() {
        chaveModel = new ChaveModel();

        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);

        Example<ChaveModel> example = Example.of(chaveModel,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        when(chaveServiceMock.buscarTudo(any()))
                .thenReturn(List.of(new ChaveModel()));

        List<ChavePixResponse> chavePixResponses = chavePixBusiness.buscarChaves(example);

        assertEquals(1, chavePixResponses.size());
    }
    @Test
    void testAtualizarChave() {
        when(chaveServiceMock.update(any(), any()))
                .thenReturn(chaveModel);
        chaveModel.setId(ID);

        ChaveModel actualResponse = chavePixBusiness.atualizar(ID, ChaveMassa.atualizarChavePixRequest());

        assertEquals(ID, actualResponse.getId());
    }
}