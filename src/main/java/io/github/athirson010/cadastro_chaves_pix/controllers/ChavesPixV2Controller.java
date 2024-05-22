package io.github.athirson010.cadastro_chaves_pix.controllers;

import io.github.athirson010.cadastro_chaves_pix.business.ChavePixV2Business;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ChavePixResponse> buscarChaves(
//            FiltroChavePixRequest request) {
//
//        if (request.getDataInclusao() != null & request.getDataInativacao() != null) {
//            throw new ValidacaoException("Não é permitido a combinacao de filtros. Data Inclusao & Data Inativacao");
//        }
//
//        if (request.getId() != null &&
//                (request.getTipoChave() != null ||
//                        request.getAgencia() != null ||
//                        request.getDataInclusao() != null ||
//                        request.getDataInativacao() != null ||
//                        request.getNomeCorrentista() != null ||
//                        request.getConta() != null)) {
//            throw new ValidacaoException("Não é permitido a combinacao de filtros. ID com outros filtros");
//        }
//
//        ChaveModelV2 filtro = ChaveV2Mapper.of(request);
//
//        Example<ChaveModelV2> example = Example.of(filtro,
//                ExampleMatcher.matchingAll()
//                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
//                        .withIgnoreNullValues()
//                        .withIgnorePaths("dataInclusao", "dataInativacao")
//                        .withIgnoreCase());
//
//        return business.buscarChaves(example);
//    }

}
