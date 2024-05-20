package io.github.athirson010.cadastro_chaves_pix.repository;


import io.github.athirson010.cadastro_chaves_pix.domains.entity.ContaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends MongoRepository<ContaEntity, String> {
    Optional<ContaEntity> findByNumeroContaAndNumeroAgencia(String numeroConta, String numeroAgencia);
}
