package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.adapters;

import io.github.athirson010.cadastro_chaves_pix.application.ports.output.ContaRepositoryPort;
import io.github.athirson010.cadastro_chaves_pix.domain.entities.Conta;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.ContaId;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroAgencia;
import io.github.athirson010.cadastro_chaves_pix.domain.valueobjects.NumeroConta;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ContaDocument;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.mappers.ContaDocumentMapper;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.repositories.SpringDataContaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContaRepositoryAdapter implements ContaRepositoryPort {

    private final SpringDataContaRepository springDataRepository;

    public ContaRepositoryAdapter(SpringDataContaRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Conta salvar(Conta conta) {
        ContaDocument document = ContaDocumentMapper.toDocument(conta);
        ContaDocument savedDocument = springDataRepository.save(document);
        return ContaDocumentMapper.toDomain(savedDocument);
    }

    @Override
    public Optional<Conta> buscarPorId(ContaId id) {
        return springDataRepository.findById(id.getValue())
                .map(ContaDocumentMapper::toDomain);
    }

    @Override
    public Optional<Conta> buscarPorAgenciaEConta(NumeroAgencia agencia, NumeroConta conta) {
        return springDataRepository.findByNumeroAgenciaAndNumeroConta(
                agencia.getValue(), 
                conta.getValue()
        ).map(ContaDocumentMapper::toDomain);
    }

    @Override
    public void deletar(ContaId id) {
        springDataRepository.deleteById(id.getValue());
    }
}