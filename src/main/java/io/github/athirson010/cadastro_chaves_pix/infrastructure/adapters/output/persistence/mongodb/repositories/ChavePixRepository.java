package io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.repositories;

import io.github.athirson010.cadastro_chaves_pix.domain.enums.StatusChaveEnum;
import io.github.athirson010.cadastro_chaves_pix.infrastructure.adapters.output.persistence.mongodb.entities.ChavePixDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChavePixRepository extends MongoRepository<ChavePixDocument, String> {
    
    Optional<ChavePixDocument> findByValorChave(String valorChave);
    
    List<ChavePixDocument> findByContaId(String contaId);

    List<ChavePixDocument> findByContaIdAndStatus(String contaId, StatusChaveEnum status);

    Long countByContaIdAndStatus(String contaId, String status);
    
    Boolean existsByValorChave(String valorChave);
}