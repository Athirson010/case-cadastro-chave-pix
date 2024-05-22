package io.github.athirson010.cadastro_chaves_pix.controllers;

import io.github.athirson010.cadastro_chaves_pix.business.ChavePixBusiness;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.mappers.ChaveMapper;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.exceptions.ValidacaoException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1/chave-pix")
@Tag(name = "V1")
@RestController
public class ChavesPixController {
    private final ChavePixBusiness business;

    public ChavesPixController(ChavePixBusiness business) {
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
    @ResponseStatus(HttpStatus.OK)
    public ChaveModel atualizarChavePix(@PathVariable String id, @Valid @RequestBody AtualizarChavePixRequest request) {
        return business.atualizar(id, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChavePixResponse buscarPorId(@PathVariable String id) {
        return business.buscarPorId(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChavePixResponse> buscarChaves(
            FiltroChavePixRequest request) {

        if (request.getDataInclusao() != null & request.getDataInativacao() != null) {
            throw new ValidacaoException("Não é permitido a combinacao de filtros. Data Inclusao & Data Inativacao");
        }

        if (request.getId() != null &&
                (request.getTipoChave() != null ||
                        request.getAgencia() != null ||
                        request.getDataInclusao() != null ||
                        request.getDataInativacao() != null ||
                        request.getNomeCorrentista() != null ||
                        request.getConta() != null)) {
            throw new ValidacaoException("Não é permitido a combinacao de filtros. ID com outros filtros");
        }

        ChaveModel filtro = ChaveMapper.of(request);

        Example<ChaveModel> example = Example.of(filtro,
                ExampleMatcher.matchingAll()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                        .withIgnoreNullValues()
                        .withIgnorePaths("dataInclusao", "dataInativacao")
                        .withIgnoreCase());

        return business.buscarChaves(example);
    }
}



