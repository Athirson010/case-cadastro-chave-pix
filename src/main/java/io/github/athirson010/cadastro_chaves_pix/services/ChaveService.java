package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.entity.ChaveEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;
import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChaveService extends AbstractService {
    private final ChaveRepository repository;

    public ChaveService(ChaveRepository repository) {
        super(ContaEntity.class, repository);
        this.repository = repository;
    }

    public Optional<ChaveEntity> buscarChavePorValorChave(String chave) {
        return repository.findByValorChaveAndStatus(chave, StatusChaveEnum.ATIVA);
    }

    public int buscarQuantidadeChavesAtivasPorConta(String idConta) {
        return repository.countByContaIdAndStatus(idConta, StatusChaveEnum.ATIVA);
    }
}
