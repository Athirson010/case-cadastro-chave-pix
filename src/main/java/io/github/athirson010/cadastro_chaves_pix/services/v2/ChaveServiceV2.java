package io.github.athirson010.cadastro_chaves_pix.services.v2;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModelV2;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveV2Repository;
import io.github.athirson010.cadastro_chaves_pix.utils.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChaveServiceV2 extends AbstractService {
    private final ChaveV2Repository repository;

    public ChaveServiceV2(ChaveV2Repository repository) {
        super(ChaveModel.class, repository);
        this.repository = repository;
    }

    public Optional<ChaveModelV2> buscarChavePorValorChave(String chave) {
        return repository.findByValorChaveAndStatus(chave, StatusChaveEnum.A);
    }

    public List<ChaveModelV2> buscarTudo(Example<ChaveModelV2> example) {
        return repository.findAll(example);
    }

    public int buscarQuantidadeChavesAtivasPorConta(String contaId) {
        return repository.countByContaIdAndStatus(contaId, StatusChaveEnum.A);
    }
}
