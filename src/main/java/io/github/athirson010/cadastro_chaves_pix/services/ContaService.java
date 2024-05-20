package io.github.athirson010.cadastro_chaves_pix.services;

import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModel;
import io.github.athirson010.cadastro_chaves_pix.repository.ContaRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService extends AbstractService {
    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        super(ContaModel.class, repository);
        this.repository = repository;
    }

    public Optional<ContaModel> buscarContaPorNumeroEAgencia(String numeroConta, String numeroAgencia) {
        return repository.findByNumeroContaAndNumeroAgencia(numeroConta, numeroAgencia);
    }

    public List<ContaModel> findByCriteria(List<Criteria> criteriaList) {
        Query query = new Query();
        if (criteriaList != null && !criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }
        return mongoTemplate.find(query, ContaModel.class);
    }
}
