package io.github.athirson010.cadastro_chaves_pix.repository;

import io.github.athirson010.cadastro_chaves_pix.domains.models.ContaModelV2;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaV2Repository extends MongoRepository<ContaModelV2, String> {
    Optional<ContaModelV2> findByNumeroContaAndNumeroAgencia(String numeroConta, String numeroAgencia);
}
