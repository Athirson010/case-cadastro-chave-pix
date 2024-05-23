package io.github.athirson010.cadastro_chaves_pix.controllers;

import io.github.athirson010.cadastro_chaves_pix.business.ChavePixV2Business;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.v2.ContaResponseV2;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ContaV2Mapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v2/chave-pix")
@Tag(name = "V2")
@RestController
public class ChavesPixV2Controller {
    private final ChavePixV2Business business;

    public ChavesPixV2Controller(ChavePixV2Business business) {
        this.business = business;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CadastroChavePixResponse cadastrarChavePix(@Valid @RequestBody CadastroChavePixRequest body) {
        return business.criarChaveComConta(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChavePixResponse inativarChavePix(@PathVariable String id) {
        return business.inativarChavePix(id);
    }

    @PutMapping("/{id}")
    public ContaResponseV2 atualizarChavePix(@Valid @RequestBody AtualizarChavePixRequest request,
                                             @PathVariable String id) {
        return business.atualizarChavePix(id, request);
    }

    //TODO retorno de 204, e um badrequest caso a request não seja valida.
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContaResponseV2> buscarChaves(
            FiltroChavePixRequest request) {

        FiltroChavePixRequest.validarFiltro(request);

        ChaveModelV2 filtroChave = ChaveV2Mapper.of(request);
        filtroChave.setStatus(StatusChaveEnum.ATIVA);
        ContaModelV2 filtroConta = ContaV2Mapper.of(request);

        Example<ChaveModelV2> exampleChave = Example.of(filtroChave,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        Example<ContaModelV2> exampleConta = Example.of(filtroConta,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnoreCase());

        return business.buscarChaves(exampleChave, exampleConta);
    }

}
