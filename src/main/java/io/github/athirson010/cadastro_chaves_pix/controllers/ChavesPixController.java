package io.github.athirson010.cadastro_chaves_pix.controllers;

import io.github.athirson010.cadastro_chaves_pix.business.ChavePixBusiness;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.requests.CadastroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.CadastroChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.domains.dtos.responses.ChavePixResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/v1/chave-pix")
@Tag(name = "Chave PIX")
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChavePixResponse buscarPorId(@PathVariable String id) {
        return business.buscarPorId(id);
    }
}

