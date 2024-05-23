package io.github.athirson010.cadastro_chaves_pix.business;

import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.github.athirson010.cadastro_chaves_pix.services.ChaveService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChavePixBusinessTest {
    @Mock
    private ChaveService chaveServiceMock;
    @InjectMocks
    private ChavePixBusiness chavePixBusiness;
    CadastroChavePixRequest request = ChaveMassa.cadastroChavePixRequest();
    ChaveModel chaveModel = ChaveMapper.of(request);
    static String ID = "10";
    public ChavePixBusinessTest() {
        MockitoAnnotations.initMocks(this);
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
    void inativarChavePixChaveErroInativa() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);

        chaveModel.setStatus(StatusChaveEnum.INATIVA);

        ValidacaoException exception = assertThrows(ValidacaoException.class,
                () -> chavePixBusiness.inativarChavePix(ID));
        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void inativarChavePixChaveSucesso() {
        when(chaveServiceMock.findById(anyString()))
                .thenReturn(chaveModel);
        chaveModel.setId(ID);

        when(chaveServiceMock.update(any(), any()))
                .thenReturn(chaveModel);

        ChavePixResponse actualResponse = chavePixBusiness.inativarChavePix(ID);

        assertEquals(ID, actualResponse.getId());
    }
}