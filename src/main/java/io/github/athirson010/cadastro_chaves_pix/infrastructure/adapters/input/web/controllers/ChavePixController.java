package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.controllers;

import io.github.athirson010.cadastro_chaves_pix.application.ports.input.AtualizarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.BuscarChavesPixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.CadastrarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.InativarChavePixUseCase;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.AtualizarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.CadastrarChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.requests.FiltroChavePixRequest;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.dto.responses.ChavePixResponse;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.input.web.mappers.ChavePixWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chaves-pix")
public class ChavePixController {

    private final CadastrarChavePixUseCase casoDeUsoCadastrarChavePix;
    private final AtualizarChavePixUseCase casoDeUsoAtualizarChavePix;
    private final InativarChavePixUseCase casoDeUsoInativarChavePix;
    private final BuscarChavesPixUseCase casoDeUsoBuscarChavesPix;

    public ChavePixController(CadastrarChavePixUseCase casoDeUsoCadastrarChavePix,
                             AtualizarChavePixUseCase casoDeUsoAtualizarChavePix,
                             InativarChavePixUseCase casoDeUsoInativarChavePix,
                             BuscarChavesPixUseCase casoDeUsoBuscarChavesPix) {
        this.casoDeUsoCadastrarChavePix = casoDeUsoCadastrarChavePix;
        this.casoDeUsoAtualizarChavePix = casoDeUsoAtualizarChavePix;
        this.casoDeUsoInativarChavePix = casoDeUsoInativarChavePix;
        this.casoDeUsoBuscarChavesPix = casoDeUsoBuscarChavesPix;
    }

    @PostMapping
    public ResponseEntity<ChavePixResponse> cadastrar(@Valid @RequestBody CadastrarChavePixRequest requisicao) {
        ChavePix chavePix = casoDeUsoCadastrarChavePix.executar(ChavePixWebMapper.toCommand(requisicao));
        ChavePixResponse resposta = ChavePixWebMapper.toResponse(chavePix);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChavePixResponse> atualizar(@PathVariable String identificador, 
                                                     @Valid @RequestBody AtualizarChavePixRequest requisicao) {
        ChavePix chavePix = casoDeUsoAtualizarChavePix.executar(ChavePixWebMapper.toCommand(identificador, requisicao));
        ChavePixResponse resposta = ChavePixWebMapper.toResponse(chavePix);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable String identificador) {
        casoDeUsoInativarChavePix.executar(ChavePixId.of(identificador));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChavePixResponse> buscarPorId(@PathVariable String identificador) {
        Optional<ChavePix> chavePix = casoDeUsoBuscarChavesPix.buscarPorId(ChavePixId.of(identificador));
        return chavePix
                .map(chave -> ResponseEntity.ok(ChavePixWebMapper.toResponse(chave)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChavePixResponse>> buscarComFiltro(FiltroChavePixRequest filtro) {
        List<ChavePix> chaves = casoDeUsoBuscarChavesPix.buscarComFiltro(ChavePixWebMapper.toQuery(filtro));
        List<ChavePixResponse> respostas = chaves.stream()
                .map(ChavePixWebMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respostas);
    }
}