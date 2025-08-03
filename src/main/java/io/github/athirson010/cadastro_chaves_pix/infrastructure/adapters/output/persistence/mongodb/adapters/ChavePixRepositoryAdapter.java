package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.adapters;

import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ChavePixRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.ChavePix;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ChavePixValue;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ChavePixDocument;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.mappers.ChavePixDocumentMapper;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.repositories.SpringDataChavePixRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChavePixRepositoryAdapter implements ChavePixRepositoryPort {

    private final SpringDataChavePixRepository springDataRepository;

    public ChavePixRepositoryAdapter(SpringDataChavePixRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public ChavePix salvar(ChavePix chavePix) {
        ChavePixDocument document = ChavePixDocumentMapper.toDocument(chavePix);
        ChavePixDocument savedDocument = springDataRepository.save(document);
        return ChavePixDocumentMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<ChavePix> buscarPorId(ChavePixId id) {
        return springDataRepository.findById(id.getValue())
                .map(ChavePixDocumentMapper::toDomain);
    }

    @Override
    public Optional<ChavePix> buscarPorValor(ChavePixValue valor) {
        return springDataRepository.findByValorChave(valor.getValue())
                .map(ChavePixDocumentMapper::toDomain);
    }

    @Override
    public List<ChavePix> buscarPorContaId(ContaId contaId) {
        return springDataRepository.findByContaId(contaId.getValue())
                .stream()
                .map(ChavePixDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChavePix> buscarComFiltros(Object... filtros) {
        if (filtros.length == 0) {
            return springDataRepository.findAll()
                    .stream()
                    .map(ChavePixDocumentMapper::toDomain)
                    .collect(Collectors.toList());
        }
        
        ChavePixDocument probe = new ChavePixDocument();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        
        Example<ChavePixDocument> example = Example.of(probe, matcher);
        
        return springDataRepository.findAll(example)
                .stream()
                .map(ChavePixDocumentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long contarChavesAtivasPorConta(ContaId contaId) {
        return springDataRepository.countByContaIdAndStatusAtiva(contaId.getValue());
    }

    @Override
    public void deletar(ChavePixId id) {
        springDataRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existePorValor(ChavePixValue valor) {
        return springDataRepository.existsByValorChave(valor.getValue());
    }
}