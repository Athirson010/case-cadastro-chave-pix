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

    private final CadastrarChavePixUseCase cadastrarChavePixUseCase;
    private final AtualizarChavePixUseCase atualizarChavePixUseCase;
    private final InativarChavePixUseCase inativarChavePixUseCase;
    private final BuscarChavesPixUseCase buscarChavesPixUseCase;

    public ChavePixController(CadastrarChavePixUseCase cadastrarChavePixUseCase,
                             AtualizarChavePixUseCase atualizarChavePixUseCase,
                             InativarChavePixUseCase inativarChavePixUseCase,
                             BuscarChavesPixUseCase buscarChavesPixUseCase) {
        this.cadastrarChavePixUseCase = cadastrarChavePixUseCase;
        this.atualizarChavePixUseCase = atualizarChavePixUseCase;
        this.inativarChavePixUseCase = inativarChavePixUseCase;
        this.buscarChavesPixUseCase = buscarChavesPixUseCase;
    }

    @PostMapping
    public ResponseEntity<ChavePixResponse> cadastrar(@Valid @RequestBody CadastrarChavePixRequest request) {
        ChavePix chavePix = cadastrarChavePixUseCase.executar(ChavePixWebMapper.toCommand(request));
        ChavePixResponse response = ChavePixWebMapper.toResponse(chavePix);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChavePixResponse> atualizar(@PathVariable String id, 
                                                     @Valid @RequestBody AtualizarChavePixRequest request) {
        ChavePix chavePix = atualizarChavePixUseCase.executar(ChavePixWebMapper.toCommand(id, request));
        ChavePixResponse response = ChavePixWebMapper.toResponse(chavePix);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativar(@PathVariable String id) {
        inativarChavePixUseCase.executar(ChavePixId.of(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChavePixResponse> buscarPorId(@PathVariable String id) {
        Optional<ChavePix> chavePix = buscarChavesPixUseCase.buscarPorId(ChavePixId.of(id));
        return chavePix
                .map(chave -> ResponseEntity.ok(ChavePixWebMapper.toResponse(chave)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ChavePixResponse>> buscarComFiltro(FiltroChavePixRequest filtro) {
        List<ChavePix> chaves = buscarChavesPixUseCase.buscarComFiltro(ChavePixWebMapper.toQuery(filtro));
        List<ChavePixResponse> responses = chaves.stream()
                .map(ChavePixWebMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}