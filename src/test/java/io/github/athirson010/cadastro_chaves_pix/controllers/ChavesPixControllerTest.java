package io.github.athirson010.cadastro_chaves_pix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.athirson010.cadastro_chaves_pix.business.ChavePixBusiness;
import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ChavesPixControllerTest {
    private MockMvc mockMvc;
    @Mock
    private ChavePixBusiness business;
    @InjectMocks
    private ChavesPixController controller;
    ObjectMapper objectMapper = new ObjectMapper();
    CadastroChavePixResponse respostaID = new CadastroChavePixResponse("10");
    ChaveModel modelo = ChaveMapper.of(ChaveMassa.cadastroChavePixRequest());

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCadastrarChavePix() throws Exception {
        when(business.criarChaveComConta(any(CadastroChavePixRequest.class))).thenReturn(respostaID);

        mockMvc.perform(post("/v1/chave-pix")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ChaveMassa.cadastroChavePixRequest())));

        verify(business, times(1)).criarChaveComConta(any(CadastroChavePixRequest.class));
    }

    @Test
    public void testInativarChavePix() throws Exception {
        ChavePixResponse response = ChaveMapper.of(modelo);

        when(business.inativarChavePix(anyString())).thenReturn(response);

        mockMvc.perform(delete("/v1/chave-pix/{id}", "123"))
                .andExpect(status().isOk());

        verify(business, times(1)).inativarChavePix("123");
    }

    @Test
    public void testAtualizarChavePix() throws Exception {
        AtualizarChavePixRequest request = new AtualizarChavePixRequest();
        request.setNomeCorrentista("Teste grande");
        request.setTipoConta(TipoContaEnum.CORRENTE);

        ChaveModel response = new ChaveModel();

        when(business.atualizar(anyString(), any(AtualizarChavePixRequest.class))).thenReturn(response);

        mockMvc.perform(put("/v1/chave-pix/{id}", "123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(business, times(1)).atualizar(anyString(), any(AtualizarChavePixRequest.class));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        ChavePixResponse response = ChaveMapper.of(modelo);

        when(business.buscarPorId(anyString())).thenReturn(response);

        mockMvc.perform(get("/v1/chave-pix/{id}", "123"))
                .andExpect(status().isOk());

        verify(business, times(1)).buscarPorId("123");
    }

    @Test
    public void testBuscarChaves() throws Exception {
        List<ChavePixResponse> responseList = new ArrayList<>();

        when(business.buscarChaves(any(Example.class))).thenReturn(responseList);

        mockMvc.perform(get("/v1/chave-pix")
                        .param("dataInclusao", "2023-01-01")
                        .param("dataInativacao", "2023-01-02"))
                .andExpect(status().isUnprocessableEntity());

        verify(business, times(0)).buscarChaves(any(Example.class));
    }
    @Test
    void testBuscarChavesDataInclusaoAndDataInativacao() {
        FiltroChavePixRequest request = new FiltroChavePixRequest();
        request.setDataInclusao(LocalDate.now());
        request.setDataInativacao(LocalDate.now());

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            controller.buscarChaves(request);
        });

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }

    @Test
    void testBuscarChavesIdComOutroFiltro() {
        FiltroChavePixRequest request = new FiltroChavePixRequest();
        request.setId("1");
        request.setTipoChave(TipoChaveEnum.CPF);
        request.setDataInativacao(LocalDate.now());

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            controller.buscarChaves(request);
        });

        assertEquals("422 UNPROCESSABLE_ENTITY", exception.getMessage());
    }


    @Test
    void testBuscarChavesSuccess() {
        FiltroChavePixRequest request = new FiltroChavePixRequest();
        request.setNomeCorrentista("John Doe");

        ChaveModel filtro = new ChaveModel();
        filtro.setNomeCorrentista("John Doe");
        filtro.setStatus(StatusChaveEnum.ATIVA);

        Example<ChaveModel> example = Example.of(filtro,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        List<ChavePixResponse> expectedResponse = List.of(new ChavePixResponse());
        when(business.buscarChaves(example)).thenReturn(expectedResponse);

        List<ChavePixResponse> response = controller.buscarChaves(request);

        assertNotNull(response);
        assertEquals(expectedResponse.size(), response.size());
        verify(business, times(1)).buscarChaves(example);
    }


}