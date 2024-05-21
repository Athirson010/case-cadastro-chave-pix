package io.github.athirson010.cadastro_chaves_pix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.athirson010.cadastro_chaves_pix.business.ChavePixBusiness;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoContaEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static io.github.athirson010.cadastro_chaves_pix.domains.enums.TipoPessoaEnum.FISICA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ChavesPixControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ChavePixBusiness business;

    @InjectMocks
    private ChavesPixController controller;

    ObjectMapper objectMapper = new ObjectMapper();
    CadastroChavePixResponse respostaID = new CadastroChavePixResponse("10");
    CadastroChavePixRequest cadastroRequest = new CadastroChavePixRequest(TipoChaveEnum.CPF, "4827447841",
            TipoContaEnum.CORRENTE, "1234", "12345678", "TESTE", "TESTE", FISICA);

    ChaveModel modelo = ChaveMapper.of(cadastroRequest);

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
                .content(objectMapper.writeValueAsString(cadastroRequest)));

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

}