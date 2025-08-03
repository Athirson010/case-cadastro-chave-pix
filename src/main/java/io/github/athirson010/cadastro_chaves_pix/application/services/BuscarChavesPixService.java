package io.github.athirson010.cadastro_chaves_pix.application.services;

import io.github.athirson010.cadastro_chaves_pix.application.dto.FiltroChavePixQuery;
import io.github.athirson010.cadastro_chaves_pix.application.ports.input.BuscarChavesPixUseCase;
import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;

import java.util.List;
import java.util.Optional;

public class BuscarChavesPixService implements BuscarChavesPixUseCase {

    private final ChavePixRepositoryPort chavePixRepository;

    public BuscarChavesPixService(ChavePixRepositoryPort chavePixRepository) {
        this.chavePixRepository = chavePixRepository;
    }

    @Override
    public List<ChavePix> buscarComFiltro(FiltroChavePixQuery filtro) {
        return chavePixRepository.buscarComFiltros(filtro);
    }

    @Override
    public Optional<ChavePix> buscarPorId(ChavePixId id) {
        return chavePixRepository.buscarPorId(id);
    }
}