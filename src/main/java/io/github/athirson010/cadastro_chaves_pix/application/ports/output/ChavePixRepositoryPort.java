package io.github.athirson010.cadastro_chaves_pix.application.ports.output;

import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixValue;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;

import java.util.List;
import java.util.Optional;

public interface ChavePixRepositoryPort {
    ChavePix salvar(ChavePix chavePix);
    Optional<ChavePix> buscarPorId(ChavePixId id);
    Optional<ChavePix> buscarPorValor(ChavePixValue valor);
    List<ChavePix> buscarPorContaId(ContaId contaId);
    List<ChavePix> buscarComFiltros(Object... filtros);
    long contarChavesAtivasPorConta(ContaId contaId);
    void deletar(ChavePixId id);
    boolean existePorValor(ChavePixValue valor);
}