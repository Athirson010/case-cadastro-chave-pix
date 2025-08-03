package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.repositories;

import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ContaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataContaRepository extends MongoRepository<ContaDocument, String> {
    
    @Query("{ 'numeroAgencia': ?0, 'numeroConta': ?1 }")
    Optional<ContaDocument> findByNumeroAgenciaAndNumeroConta(String numeroAgencia, String numeroConta);
}