package io.github.athirson010.cadastro_chaves_pix.repository;


import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends MongoRepository<ContaModel, String> {
    Optional<ContaModel> findByNumeroContaAndNumeroAgencia(String numeroConta, String numeroAgencia);
}
