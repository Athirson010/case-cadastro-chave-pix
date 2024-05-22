package io.github.athirson010.cadastro_chaves_pix.services.v2;

import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import io.github.athirson010.cadastro_chaves_pix.repository.ContaV2Repository;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaServiceV2 extends AbstractService {
    private final ContaV2Repository repository;

    public ContaServiceV2(ContaV2Repository repository) {
        super(ContaModelV2.class, repository);
        this.repository = repository;
    }

    public Optional<ContaModelV2> buscarContaPorNumeroContaENumeroAgencia(String numeroConta, String numeroAgencia) {
        return repository.findByNumeroContaAndNumeroAgencia(numeroConta, numeroAgencia);
    }

    public List<ContaModelV2> buscarContasPorIds(List<String> contas) {
        return repository.findByIdIn(contas);
    }
}
