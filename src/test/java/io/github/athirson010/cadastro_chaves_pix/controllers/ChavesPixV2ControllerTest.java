package io.github.athirson010.cadastro_chaves_pix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.athirson010.cadastro_chaves_pix.business.ChavePixV2Business;
import io.github.athirson010.cadastro_chaves_pix.dados.ChaveMassa;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ContaResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChavesPixV2ControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ChavePixV2Business business;

    @InjectMocks
    private ChavesPixV2Controller controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    ChaveModelV2 chave = ChaveV2Mapper.of(ChaveMassa.cadastroChavePixRequest(), new AbstractModel("01", LocalDate.now()));
    ContaModelV2 conta = ContaV2Mapper.of(ChaveMassa.cadastroChavePixRequest());
    CadastroChavePixResponse cadastroChavePixResponse = new CadastroChavePixResponse("1");

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCadastrarChavePix() throws Exception {
        when(business.criarChaveComConta(any(CadastroChavePixRequest.class)))
                .thenReturn(cadastroChavePixResponse);

        mockMvc.perform(post("/v2/chave-pix")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ChaveMassa.cadastroChavePixRequest())))
                .andExpect(status().isOk());
    }

    @Test
    public void testInativarChavePix() throws Exception {
        when(business.inativarChavePix(any(String.class)))
                .thenReturn(ChaveMapper.of(chave, conta));

        mockMvc.perform(delete("/v2/chave-pix/{id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAtualizarChavePix() throws Exception {
        AtualizarChavePixRequest request = ChaveMassa.atualizarChavePixRequest();
        ChaveModelV2 chave = ChaveV2Mapper.of(request);
        ContaModelV2 conta = ContaV2Mapper.of(request);

        ContaResponseV2 response = ContaV2Mapper.of(conta, List.of(chave));

        when(business.atualizarChavePix(any(), any()))
                .thenReturn(response);

        mockMvc.perform(put("/v2/chave-pix/{id}", "test-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
//
//    @Test
//    public void testBuscarChaves() throws Exception {
//        List<ContaResponseV2> response = new ArrayList<>();
//
//        when(business.buscarChaves(any(), any())).thenReturn(response);
//
//        mockMvc.perform(get("/v2/chave-pix")
//                        .param("id", "test-id"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"));
//    }
//
//    @Test
//    public void testBuscarChavesComValidacaoException() throws Exception {
//        FiltroChavePixRequest request = new FiltroChavePixRequest();
//        request.setDataInclusao("2023-01-01");
//        request.setDataInativacao("2023-01-02");
//
//        mockMvc.perform(get("/v2/chave-pix")
//                        .param("dataInclusao", "2023-01-01")
//                        .param("dataInativacao", "2023-01-02"))
//                .andExpect(status().isBadRequest());
//    }
}