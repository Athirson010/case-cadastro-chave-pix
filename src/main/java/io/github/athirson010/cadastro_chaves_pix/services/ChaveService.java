package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ChaveModel;
import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModel;
import io.github.athirson010.cadastro_chaves_pix.repository.ChaveRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChaveService extends AbstractService {
    private final ChaveRepository repository;

    public ChaveService(ChaveRepository repository) {
        super(ContaModel.class, repository);
        this.repository = repository;
    }

    public Optional<ChaveModel> buscarChavePorValorChave(String chave) {
        return repository.findByValorChaveAndStatus(chave, StatusChaveEnum.ATIVA);
    }

    public int buscarQuantidadeChavesAtivasPorConta(String idConta) {
        return repository.countByContaIdAndStatus(idConta, StatusChaveEnum.ATIVA);
    }

    public List<ChaveModel> findByCriteria(List<Criteria> criteriaList) {
        Query query = new Query();
        if (criteriaList != null && !criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return mongoTemplate.find(query, ChaveModel.class);
    }

}
