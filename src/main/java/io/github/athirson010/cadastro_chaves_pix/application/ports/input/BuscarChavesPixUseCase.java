package io.github.athirson010.cadastro_chaves_pix.application.ports.input;

import io.github.athirson010.cadastro_chaves_pix.application.dto.FiltroChavePixQuery;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;

import java.util.List;
import java.util.Optional;

public interface BuscarChavesPixUseCase {
    List<ChavePix> buscarComFiltro(FiltroChavePixQuery filtro);
    Optional<ChavePix> buscarPorId(ChavePixId id);
}