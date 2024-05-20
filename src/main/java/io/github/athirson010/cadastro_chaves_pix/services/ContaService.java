package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;
import io.github.athirson010.cadastro_chaves_pix.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService extends AbstractService {
    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        super(ContaEntity.class, repository);
        this.repository = repository;
    }

    public Optional<ContaEntity> buscarContaPorNumeroEAgencia(String numeroConta, String numeroAgencia) {
        return repository.findByNumeroContaAndNumeroAgencia(numeroConta, numeroAgencia);
    }
}
